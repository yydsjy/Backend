package com.example.spring.aop.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServiceProxy1 implements UserService{
    private UserService userService;
    public UserServiceProxy1(UserService userService){
        this.userService = userService;
    }

    public void createUser() {
        userService.createUser();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
    }

}
