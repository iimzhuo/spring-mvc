package com.Re.controller;

import com.Re.MyModelAndView;
import com.Re.annotation.*;
import com.Re.service.UserService;

import java.util.ArrayList;
import java.util.List;

@MyRequestMapping("/user")
@MyController
public class UserController {

    @MyAutowired
    @MyQualifier("userServiceImpl")
    private  UserService userService;

    @MyRequestMapping("/select")
    public String selectAll(){
        System.out.println("查询所有用户");
        return "success";
    }

    @MyRequestMapping("/selectById")
    public String  selectById(){
        System.out.println("查询所有的");
        return "success";
    }

    @MyRequestMapping("/selectAll")
    public MyModelAndView sayOk(@MyRequestParam(value="a") Integer a1,@MyRequestParam(value="b") Integer b1){
        List<Integer> list = userService.selectAll(a1, b1);
        MyModelAndView myModelAndView = new MyModelAndView("index");
        myModelAndView.addAttribute("list",list);
        return myModelAndView;
    }

    @MyRequestMapping("/_selectAll")
    public MyModelAndView sayOk(@MyRequestParam(value="a") String a1,@MyRequestParam(value="b") Integer b1){
        List<Integer> list = userService.select(a1,b1);
        MyModelAndView myModelAndView = new MyModelAndView("index");
        myModelAndView.addAttribute("list",list);

        System.out.println("Test success!");
        return myModelAndView;
    }
}
