package com.Re.dao.daoImpl;

import com.Re.annotation.MyRepository;
import com.Re.dao.UserDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MyRepository
public class UserDaoImpl implements UserDao {

    public void sayOk(int a,int b){
        System.out.println("ok!");
    }

    @Override
    public List<Integer> selectAll(int a, int b) {
        System.out.println("调用userDao中方法，参数如下： a="+a+" b="+b);
        return new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
    }

    @Override
    public List<Integer> select(String a, int b) {
        System.out.println("调用userDao中方法，参数如下： a="+a+" b="+b);
        return new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
    }


}
