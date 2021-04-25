package com.example.spring.jdbc.service;

import com.example.spring.jdbc.dao.EmployeeDao;
import com.example.spring.jdbc.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
@Service
@Transactional
public class BatchService {
    @Resource
    private EmployeeDao employeeDao;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
