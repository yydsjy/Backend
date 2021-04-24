package com.example.spring.aop.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    public void insert(){
        System.out.println("UserDao");
    }
}
