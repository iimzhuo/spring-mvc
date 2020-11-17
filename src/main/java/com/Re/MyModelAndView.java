package com.Re;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型视图
 */
public class MyModelAndView {
    private String view;
    private Map<String ,Object> map=new HashMap<>();

    public MyModelAndView(String view) {
        this.view = view;
    }

    public MyModelAndView(){

    }

    public void addAttribute(String key,Object obj){
        map.put(key,obj);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MyModelAndView{" +
                "view='" + view + '\'' +
                ", map=" + map +
                '}';
    }
}
