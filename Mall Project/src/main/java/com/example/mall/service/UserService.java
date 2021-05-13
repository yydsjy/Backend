package com.example.mall.service;

import com.example.mall.exception.BusinessException;
import com.example.mall.model.pojo.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    User getUser();

    void register(String userName,String password) throws BusinessException, NoSuchAlgorithmException;

    User login(String userName, String password) throws BusinessException;

    void updateUserInfo(User user) throws BusinessException;

    boolean checkAdminRole(User user);
}
