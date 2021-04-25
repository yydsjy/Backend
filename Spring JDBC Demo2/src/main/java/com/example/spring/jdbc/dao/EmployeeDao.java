package com.example.spring.jdbc.dao;

import com.example.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Employee employee){
        String sql = "insert into employee(eno,ename,salary,dname,hiredate) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{
                employee.getEno(),employee.getEname(),employee.getSalary(),employee.getDname(),employee.getHiredate()});
    }
}
