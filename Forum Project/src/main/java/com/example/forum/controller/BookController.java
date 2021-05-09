package com.example.forum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.forum.entity.*;
import com.example.forum.service.BookService;
import com.example.forum.service.CategoryService;
import com.example.forum.service.EvaluationService;
import com.example.forum.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

    @GetMapping("/")
    public ModelAndView showIndex(){
        ModelAndView mav = new ModelAndView("/index");
        List<Category> list = categoryService.selectAll();
        mav.addObject("categoryList",list);
        return mav;
    }

    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId,String order, Integer p){
        if(p==null){
            p=1;
        }
        return bookService.paging(categoryId,order,p,10);
    }

    @GetMapping("/book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){
        Book book = bookService.selectById(id);
        List<Evaluation> evaluations = evaluationService.selectByBookId(id);
        Member member = (Member)session.getAttribute("loginMember");
        ModelAndView mav = new ModelAndView("/detail");
        if (member!=null){
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), book.getBookId());
            mav.addObject("memberReadState",memberReadState);
        }

        mav.addObject("book",book);
        mav.addObject("evaluations",evaluations);

        return mav;
    }





}
