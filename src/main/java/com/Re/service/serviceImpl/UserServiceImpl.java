package com.Re.service.serviceImpl;

import com.Re.annotation.MyAutowired;
import com.Re.annotation.MyQualifier;
import com.Re.annotation.MyService;
import com.Re.dao.UserDao;
import com.Re.service.UserService;

import java.util.List;

@MyService
public class UserServiceImpl implements UserService {
    @MyAutowired
    @MyQualifier("userDaoImpl")
    private UserDao userDao;

    @Override
    public void sayOk(int a,int b) {
        userDao.sayOk( a, b);
    }

    @Override
    public List<Integer> selectAll(int a, int b) {
        return userDao.selectAll(a,b);
    }

    @Override
    public List<Integer> select(String a, int b) {
        return userDao.select(a,b);
    }
}
