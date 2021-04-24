package com.example.spring.aop.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServiceProxy implements UserService{
    private UserService userService;
    public UserServiceProxy(UserService userService){
        this.userService = userService;
    }

    public void createUser() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
        userService.createUser();
    }
}
