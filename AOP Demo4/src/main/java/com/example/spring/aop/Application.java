package com.example.spring.aop;

import com.example.spring.aop.service.UserService;
import com.example.spring.aop.service.UserServiceImpl;
import com.example.spring.aop.service.UserServiceProxy;
import com.example.spring.aop.service.UserServiceProxy1;

public class Application {
    public static void main(String[] args) {
        UserService userService = new UserServiceProxy1(new UserServiceProxy(new UserServiceImpl()));
        userService.createUser();
    }
}
