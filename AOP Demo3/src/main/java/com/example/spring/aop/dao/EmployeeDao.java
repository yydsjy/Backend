package com.example.spring.aop.dao;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {
    public void insert(){
        System.out.println("EmployeeDao");
    }
}
