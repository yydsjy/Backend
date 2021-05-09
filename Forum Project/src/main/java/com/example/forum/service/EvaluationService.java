package com.example.forum.service;

import com.example.forum.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    public List<Evaluation> selectByBookId(Long bookId);
}
