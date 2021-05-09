package com.example.forum.service;

import com.example.forum.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;
    @Transactional
    public void batchImport(){
        for (int i = 0; i < 5; i++) {
            testMapper.insertSample();
        }
    }
}
