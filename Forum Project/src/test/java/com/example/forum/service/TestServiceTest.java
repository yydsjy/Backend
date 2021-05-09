package com.example.forum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.forum.mapper.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestServiceTest {
    @Resource
    private TestService testService;

    @Resource
    private TestMapper testMapper;

    @Test
    public void batchImport() {
        testService.batchImport();
    }
    @Test
    public void testInsert(){
        com.example.forum.entity.Test test = new com.example.forum.entity.Test();
        test.setContent("nil");
        testMapper.insert(test);
    }
    @Test
    public void testUpdate(){
        com.example.forum.entity.Test test = testMapper.selectById(22);
        test.setContent("mike");
        testMapper.updateById(test);
    }
    @Test
    public void testDelete(){
        testMapper.deleteById(24);
    }
    @Test
    public void testSelect(){
        QueryWrapper<com.example.forum.entity.Test> queryWrapper = new QueryWrapper<com.example.forum.entity.Test>();
//        queryWrapper.eq("id",23);
        queryWrapper.gt("id",20);
        List<com.example.forum.entity.Test> tests = testMapper.selectList(queryWrapper);
        System.out.println(tests);
    }


}