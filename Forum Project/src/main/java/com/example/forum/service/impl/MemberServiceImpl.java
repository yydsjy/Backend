package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.forum.entity.Evaluation;
import com.example.forum.entity.Member;
import com.example.forum.entity.MemberReadState;
import com.example.forum.mapper.EvaluationMapper;
import com.example.forum.mapper.MemberMapper;
import com.example.forum.mapper.MemberReadStateMapper;
import com.example.forum.service.MemberService;
import com.example.forum.service.exception.BusinessException;
import com.example.forum.service.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> list = memberMapper.selectList(queryWrapper);
        if (list.size()>0){
            throw new BusinessException("M01","username exists");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000)+1000;
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member == null){
            throw new BusinessException("M02","Wrong Username");
        }
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if (!md5.equals(member.getPassword())){
            throw new BusinessException("M03","Wrong Password");
        }
        return member;
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState){
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        if (memberReadState==null){
            memberReadState = new MemberReadState();
            memberReadState.setBookId(bookId);
            memberReadState.setMemberId(memberId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        } else {
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }

    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy()+1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
