package com.example.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.forum.entity.Book;

public interface BookService {
    public IPage<Book> paging(Long categoryId, String order, Integer page,Integer rows);
    public Book selectById(Long bookId);
    public void updateEvaluation();
    public Book createBook(Book book);
    public Book updateBook(Book book);
    public void deleteBook(Long bookId);
}
