package com.Re;

import java.lang.reflect.Method;
import java.util.Map;

public class HandlerMapping {
    private Class<?> classInfo;

    private Method method;

    /**
     * 从请求映射的集合中查找对应的handler
     * @param controlMapping
     * @param methodMapping
     * @param handlerMapping
     * @return
     */
    public HandlerMapping FindHandler(String controlMapping, String methodMapping, Map<String ,Method> handlerMapping){
        HandlerMapping handler = new HandlerMapping();
        String url=controlMapping+methodMapping;
        if(handlerMapping.containsKey(url)){
            Method method = handlerMapping.get(url);
            handler.setMethod(method);
            Class<?> c = method.getDeclaringClass();
            handler.setClassInfo(c);
        }
        return handler;
    }

    public Class<?> getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Class<?> classInfo) {
        this.classInfo = classInfo;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "HandlerMapping{" +
                "classInfo=" + classInfo +
                "\nmethod=" + method +
                '}';
    }
}
