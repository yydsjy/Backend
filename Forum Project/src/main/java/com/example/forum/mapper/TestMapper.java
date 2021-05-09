package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
