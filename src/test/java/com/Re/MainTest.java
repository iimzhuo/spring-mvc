package com.Re;

import com.Re.annotation.MyAutowired;
import com.Re.annotation.MyRequestParam;
import com.Re.controller.UserController;
import com.Re.service.UserService;
import com.Re.service.serviceImpl.UserServiceImpl;
import com.Re.servlet.DispatcherServlet;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    @Test
    public void Test01(){
        String str="/MySpringMvc/user/selectAll";
        int i = str.indexOf("/");
        int i1 = str.indexOf("/", i + 1);
        int i2 = str.lastIndexOf("/");
        System.out.println(i);
        System.out.println(i1+" "+str.substring(i1,i2));
        System.out.println(i2+" "+str.substring(i2));
        List<Integer> list=new ArrayList<>(Arrays.asList(1,2,22));
        list.forEach(item->{
            System.out.println(item);
        });
    }

    @Test
    public void Test02() {
        String name = UserController.class.getSimpleName();
        char[] chars = name.toCharArray();
        chars[0]+=32;
        name=String.valueOf(chars);
        System.out.println(name);
    }

    @Test
    public void Test03(){
        Class<UserServiceImpl> serviceClass = UserServiceImpl.class;
        Class<?>[] interfaces = serviceClass.getInterfaces();
        for (Class<?> item:interfaces)
            System.out.println(item.getSimpleName()+"\n"+item.getName());
    }

    @Test
    public void Test04(){
        Class<?>[] interfaces = UserServiceImpl.class.getInterfaces();
        for(Class<?> c:interfaces)
            System.out.println(c.getSimpleName());
    }

    @Test
    public void Test05(){
        Field[] fields = UserServiceImpl.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields,true);
        for (Field field : fields) {
            if(field.isAnnotationPresent(MyAutowired.class)){
                String name = field.getType().getSimpleName();
                System.out.println(name);
            }
        }
    }

    @Test
    public void Test06(){
        URL resource = DispatcherServlet.class.getClassLoader().getResource("com/Re");
        System.out.println(resource.getPath().substring(1));
        System.out.println(DispatcherServlet.class.getResource("/com/Re/controller").getPath().substring(1));
    }

    @Test
    public void Test07() throws NoSuchMethodException {
        Method method = DispatcherServlet.class.getDeclaredMethod("initHandlerMapping");
        System.out.println(method.getDeclaringClass().getName());
    }

    @Test
    public void Test08(){
        String str="aaa";
        String[] split = str.split(":");
        System.out.println(split[0]);
    }

    @Test
    public void Test09() throws Exception {
        Method method = ViewResolver.class.getDeclaredMethod("printList");
        List list=new ArrayList(Arrays.asList(1,2,34,5));
        method.invoke(ViewResolver.class.newInstance(), list);
    }

    @Test
    public void Test10() throws Exception {
        Method method=UserController.class.getDeclaredMethod("sayOk",Integer.class,Integer.class);
        method.setAccessible(true);
        System.out.println(method);
        Parameter[] parameters = method.getParameters();
        Integer args[]=new Integer[parameters.length];
        int count=0;
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getType());
            args[count]=count++;
        }
        method.invoke(UserController.class.newInstance(),args);
    }

    @Test
    public void Test11(){
        URL resource = DispatcherServlet.class.getClassLoader().getResource("com");
        URL resource1 = DispatcherServlet.class.getResource("/com");
        System.out.println(resource1.getPath());
        System.out.println(resource.getPath());
    }
}
