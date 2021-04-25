package com.example.spring.jdbc.service;

import com.example.spring.jdbc.dao.EmployeeDao;
import com.example.spring.jdbc.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeService {
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private BatchService batchService;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public BatchService getBatchService() {
        return batchService;
    }

    public void setBatchService(BatchService batchService) {
        this.batchService = batchService;
    }

    public void batchImport(){

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
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public void startImport(){
        batchService.import1();
        batchService.import2();
    }
}