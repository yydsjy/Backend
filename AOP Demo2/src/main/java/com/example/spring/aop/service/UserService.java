package com.example.spring.aop.service;

import com.example.spring.aop.dao.UserDao;

public class UserService {
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("UserService");
        userDao.insert();
    }

    public String generateRandomPassword(String type,Integer length){
        System.out.println("generateRandomPassword");
        return "mike";

    }
}
