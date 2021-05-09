package com.example.forum.service;

import com.example.forum.entity.Evaluation;
import com.example.forum.entity.Member;
import com.example.forum.entity.MemberReadState;

public interface MemberService {
    public Member createMember(String username,String password,String nickname);
    public Member checkLogin(String username,String password);

    public MemberReadState selectMemberReadState(Long memberId,Long bookId);
    public MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);

    public Evaluation evaluate(Long memberId, Long bookId, Integer score,String content);
    public Evaluation enjoy(Long evaluationId);
}
