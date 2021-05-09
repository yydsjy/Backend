package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    public void updateEvaluation();
}
