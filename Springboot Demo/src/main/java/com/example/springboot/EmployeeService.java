package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    public Employee findEmployeeById(Integer eno){
        return employeeMapper.findEmployeeById(eno);
    }
}
