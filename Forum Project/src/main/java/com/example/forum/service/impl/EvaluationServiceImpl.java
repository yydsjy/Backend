package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.forum.entity.Book;
import com.example.forum.entity.Evaluation;
import com.example.forum.entity.Member;
import com.example.forum.mapper.BookMapper;
import com.example.forum.mapper.EvaluationMapper;
import com.example.forum.mapper.MemberMapper;
import com.example.forum.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    EvaluationMapper evaluationMapper;
    @Resource
    MemberMapper memberMapper;
    @Resource
    BookMapper bookMapper;

    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("state","enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> list = evaluationMapper.selectList(queryWrapper);
        for(Evaluation e:list){
            Member member = memberMapper.selectById(e.getMemberId());
            e.setMember(member);
            e.setBook(book);
        }
        return list;
    }
}
