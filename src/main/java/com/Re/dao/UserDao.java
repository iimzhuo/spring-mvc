package com.Re.dao;

import java.util.List;

public interface UserDao {
    void sayOk(int a,int b);
    List<Integer> selectAll(int a,int b);

    List<Integer> select(String a,int b);
}
