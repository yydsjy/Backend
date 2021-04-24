package com.example.spring.aop.service;

import com.example.spring.aop.dao.EmployeeDao;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void entry(){
        System.out.println("EmployeeService");
        employeeDao.insert();
    }
}
