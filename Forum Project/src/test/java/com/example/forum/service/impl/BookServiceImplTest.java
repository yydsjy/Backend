package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.forum.entity.Book;
import com.example.forum.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {
    @Resource
    private BookService bookService;

    @Test
    public void paging() {
        IPage<Book> pageObject = bookService.paging(2l,"quantity",1, 10);
        List<Book> list = pageObject.getRecords();
        for(Book b:list){
            System.out.println(b.getBookId()+" "+b.getBookName());
        }
        System.out.println(pageObject.getPages());
        System.out.println(pageObject.getTotal());
    }
}