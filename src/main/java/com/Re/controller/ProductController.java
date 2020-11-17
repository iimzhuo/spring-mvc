package com.Re.controller;

import com.Re.annotation.MyController;
import com.Re.annotation.MyRequestMapping;

@MyRequestMapping("/product")
@MyController
public class ProductController {

    @MyRequestMapping("/selectAll")
    public  String selectAll(){
        System.out.println("查询所有商品信息");
        return "success";
    }
}
