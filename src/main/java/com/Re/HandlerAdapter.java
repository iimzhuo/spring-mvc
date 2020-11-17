package com.Re;

import com.Re.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {
    private  MyModelAndView modelAndView;
    /**
     * 处理器适配器执行对应的handler
     * @param handler
     * @param req
     * @param map
     * @return
     */
    public MyModelAndView executeHandler(HandlerMapping handler, HttpServletRequest req, Map<String,Object> map){
        Method method = handler.getMethod();

        //xxx
        //获取方法上的参数及其注解信息
        Parameter[] parameters = method.getParameters();
        Object args[]=new Object[parameters.length];
        int count=0;
        for (Parameter parameter : parameters) {
            String param = parameter.getAnnotation(MyRequestParam.class).value();
            Object value = req.getParameter(param);
            args[count++]=value;
        }
        Class<?> controller = handler.getClassInfo();
        String beanName = toLowFirstWord(controller.getSimpleName());
        try {
            //Object o = controller.newInstance();
            Object o = map.get(beanName); //从容器中获取
            System.out.println(o+"\n"+method+"\n"+Arrays.toString(args));
            Integer arr[]=new Integer[]{1,2};
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType= parameterTypes[i];
                String type = parameterType.getSimpleName();
                System.out.println(type);
                if("Integer".equals(type)){
                    args[i] = Integer.parseInt(args[i].toString());
                }else if("String".equals(type)){
                    args[i]=args[i].toString();
                }
            }
            modelAndView =(MyModelAndView) method.invoke(o,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
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



    public MyModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setModelAndView(MyModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }
}
