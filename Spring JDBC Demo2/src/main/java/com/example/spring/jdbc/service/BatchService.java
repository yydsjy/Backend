package com.example.spring.jdbc.service;

import com.example.spring.jdbc.dao.EmployeeDao;
import com.example.spring.jdbc.entity.Employee;

import java.util.Date;

public class BatchService {
    private EmployeeDao employeeDao;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void import1(){
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employee.setEno(8000+i);
            employee.setEname("Mike"+i);
            employee.setSalary(1234f);
            employee.setDname("Boss");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    public void import2(){
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employee.setEno(9000+i);
            employee.setEname("Mike"+i);
            employee.setSalary(1234f);
            employee.setDname("Boss");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }
}
