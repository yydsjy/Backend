package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.forum.entity.Book;
import com.example.forum.entity.Evaluation;
import com.example.forum.entity.MemberReadState;
import com.example.forum.mapper.BookMapper;
import com.example.forum.mapper.EvaluationMapper;
import com.example.forum.mapper.MemberReadStateMapper;
import com.example.forum.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    public IPage<Book> paging(Long categoryId, String order,Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page,rows);

        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        if (categoryId!=null&&categoryId!=-1){
            queryWrapper.eq("category_id",categoryId);
        }
        if (order!=null){
            if (order.equals("quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if (order.equals("score")){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQuerryWrapper = new QueryWrapper<MemberReadState>();
        mrsQuerryWrapper.eq("book_id",bookId);
        memberReadStateMapper.delete(mrsQuerryWrapper);
        QueryWrapper<Evaluation> evaQueryWrapper = new QueryWrapper<Evaluation>();
        evaQueryWrapper.eq("book_id",bookId);
        evaluationMapper.delete(evaQueryWrapper);
    }


}
