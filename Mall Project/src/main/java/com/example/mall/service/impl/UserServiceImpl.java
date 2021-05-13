package com.example.mall.service.impl;

import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.example.mall.model.dao.UserMapper;
import com.example.mall.model.pojo.User;
import com.example.mall.service.UserService;
import com.example.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(String userName, String password) throws BusinessException, NoSuchAlgorithmException {
        User result = userMapper.selectByName(userName);
        if (result!=null){
            throw new BusinessException(ExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(MD5Utils.getMD5Str(password));
        int count = userMapper.insertSelective(user);
        if (count==0){
            throw new BusinessException(ExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName, String password) throws BusinessException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if (user==null){
            throw new BusinessException(ExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateUserInfo(User user) throws BusinessException {
        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count>1){
            throw new BusinessException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        return user.getRole().equals(2);
    }
}
