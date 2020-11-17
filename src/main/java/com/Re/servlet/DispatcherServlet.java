package com.Re.servlet;

import com.Re.HandlerAdapter;
import com.Re.HandlerMapping;
import com.Re.MyModelAndView;
import com.Re.ViewResolver;
import com.Re.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class DispatcherServlet extends HttpServlet {
    //扫描的包名
    private List<String> packageList=new ArrayList<>();
    //存储包下所有的类的全限定名
    private List<String> classNames=new ArrayList<>();
    //IOC容器
    private Map<String,Object> map=new HashMap<>();
    //handler集合
    private Map<String ,Method> handlerMapping=new HashMap<>();

    private Logger logger = Logger.getLogger("init");

    @Override
    public void init() throws ServletException {
        logger.info("初始化ioc容器");
        URL resource = this.getClass().getResource("/com/Re/config");
        String fileName = resource.getPath().substring(1);
        File file=new File(fileName);
        File[] files = file.listFiles();
        //扫描配置类，通过获取配置类上的MyComponentScan注解值来获取将要扫描的包名，为组件注册左准备
        for (File item : files) {
            String className="com.Re.config."+item.getName();
            className=className.replace(".class","");
            try {
                Class<?> c = Class.forName(className);
                if(c.isAnnotationPresent(MyConfiguration.class)){
                    if(c.isAnnotationPresent(MyComponentScan.class)){
                        MyComponentScan annotation = c.getAnnotation(MyComponentScan.class);
                        String[] value = annotation.value();
                        for(String packageName:value){
                            packageList.add(packageName);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //获取扫描包下的所有类
        initClassNames();
        //测试输出所有的全限定类名，
        System.out.println("输出所有被扫描文件的全限定类名");
        classNames.forEach(item->{
            System.out.println(item);
        });
        System.out.println("--------------------------------------\n\n");
        //初始化ioc容器
        System.out.println("注册组件");
        initContext();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        entries.forEach(item->{
            System.out.println(item.getKey()+"---------"+item.getValue());
        });
        System.out.println("--------------------------------------\n\n");
        //开启自动装配
        System.out.println("开始自动装配");
        initAutowired();
        //初始化handlerMapping
        System.out.println("初始化所有handler");
        initHandlerMapping();
        Set<Map.Entry<String, Method>> entrySet = handlerMapping.entrySet();
        entrySet.forEach(item->{
            System.out.println(item.getKey()+"--------------"+item.getValue());
        });
        System.out.println("--------------------------------------\n\n");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        //   System.out.println(uri);
        //  /MySpringMvc/user/selectAll
        int i = uri.indexOf("/");
        int i1 = uri.indexOf("/", i + 1);
        int i2 = uri.lastIndexOf("/");
        String controlMapping=uri.substring(i1,i2);
        String methodMapping=uri.substring(i2);

        //  System.out.println(controlMapping+" "+methodMapping);
        //  /user /selectAll
        //根据请求映射获取对应的handler
        HandlerMapping handler=new HandlerMapping().FindHandler(controlMapping,methodMapping,handlerMapping);
        System.out.println(handler);
        System.out.println("--------------------------------------\n\n");
        HandlerAdapter adapter=new HandlerAdapter();

        //处理器适配器去执行对应的handler
        MyModelAndView myModelAndView=null;
        if(handler.getClassInfo()!=null) {
            logger.info("开始执行handler");
            myModelAndView = adapter.executeHandler(handler, req, map);
        } else resp.getWriter().write("404 not find");
        System.out.println("ModelAndView---------------"+myModelAndView+"\n\n");
        //视图解析器解析视图
        ViewResolver viewResolver = new ViewResolver().resolverView(myModelAndView);
        System.out.println("viewResolver---------------"+viewResolver+"\n\n");

        Set<Map.Entry<String, Object>> entries = myModelAndView.getMap().entrySet();
        entries.forEach(item->{
            req.setAttribute(item.getKey(),item.getValue());
        });

        if(viewResolver.isRedirect()){
            resp.sendRedirect(viewResolver.getViewName());
        }else{
            req.getRequestDispatcher("../"+viewResolver.getViewName()).forward(req,resp);
        }
    }

    /**
     * 通过将要扫描的包名来获取所有被扫描的类名
     */
    public  void initClassNames(){
        packageList.forEach(item->{
            String packageName=item;
            item=item.replace(".","/");
            String path = this.getClass().getResource("/").getPath().substring(1) + item;
            //System.out.println(path);
            getAllFile(new File(path),packageName);
        });
    }

    /**
     * 初始化ioc容器
     */
    public void initContext(){
        if(classNames.size()==0) return ;
        classNames.forEach(item->{
            try {
                Class<?> c = Class.forName(item);
                String beanName=toLowFirstWord(c.getSimpleName()); //bean的默认id为类名首字母小写
                if(c.isAnnotationPresent(MyController.class)||c.isAnnotationPresent(MyComponent.class)){
                    map.put(toLowFirstWord(c.getSimpleName()),c.newInstance());
                }
                else if(c.isAnnotationPresent(MyService.class)){
                    MyService ser = c.getAnnotation(MyService.class);

                    if(!ser.value().trim().equals("")){
                        beanName=ser.value();
                    }
                    Object o = c.newInstance();
                    map.put(beanName,o);
                    Class<?>[] interfaces = c.getInterfaces();
                    for(Class<?> i:interfaces)
                        map.put(i.getName(),o);
                }
                else if(c.isAnnotationPresent(MyRepository.class)){
                    MyRepository annotation = c.getAnnotation(MyRepository.class);
                    if(!annotation.value().trim().equals("")){
                        beanName=annotation.value();
                    }
                    Object obj = c.newInstance();
                    map.put(beanName,obj);
                    Class<?>[] interfaces = c.getInterfaces();
                    for (Class<?> i:interfaces){
                        map.put(i.getName(),obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 首字母转化为小写
     * @param str
     * @return
     */
    public String toLowFirstWord(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        String s = String.valueOf(chars);
        return s;
    }

    /**
     * 获取包下的所有文件，取出全限定类名
     * @param file
     * @param packageName
     */
    public void getAllFile(File file,String packageName){
        File[] files = file.listFiles();
        for (File item : files) {
            if(item.isDirectory()){
                getAllFile(item,packageName+"."+item.getName());
            }else{
                classNames.add(packageName+"."+item.getName().replace(".class",""));
            }
        }
    }

    /**
     * 自动装配
     */
    public void initAutowired(){
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        entries.forEach(item->{
            Object obj = item.getValue();
            Field[] fields = obj.getClass().getDeclaredFields();
            AccessibleObject.setAccessible(fields,true);
            for (Field field : fields) {
                if(field.isAnnotationPresent(MyAutowired.class)){
                    String name =toLowFirstWord(field.getType().getSimpleName());
                    if(field.isAnnotationPresent(MyQualifier.class)){
                        String value = field.getAnnotation(MyQualifier.class).value();
                        name=value;
                    }
                    if(map.containsKey(name)){
                        try {
                            field.set(obj,map.get(name));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 初始化所有的handler
     */
    public void initHandlerMapping(){
        String path = this.getClass().getResource("/com/Re/controller").getPath().substring(1);
        File[] files = new File(path).listFiles();
        for (File file : files) {
            String className = "com.Re.controller."+file.getName().replace(".class", "");
            try {
                Class<?> c = Class.forName(className);
                String url="";
                if(c.isAnnotationPresent(MyController.class)){
                    if(c.isAnnotationPresent(MyRequestMapping.class)){
                        url=c.getAnnotation(MyRequestMapping.class).value();
                    }
                    Method[] methods = c.getDeclaredMethods();
                    for (Method method : methods) {
                        if(!method.isAnnotationPresent(MyRequestMapping.class)) continue;
                        url=url+method.getAnnotation(MyRequestMapping.class).value();
                        handlerMapping.put(url,method);
                        url=url.substring(0,url.lastIndexOf("/"));
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
