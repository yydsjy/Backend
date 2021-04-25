package com.example.spring.jdbc.service;

import com.example.spring.jdbc.dao.EmployeeDao;
import com.example.spring.jdbc.entity.Employee;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

public class EmployeeService {
    private EmployeeDao employeeDao;
    private DataSourceTransactionManager transactionManager;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void batchImport(){
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            for (int i = 0; i < 10; i++) {
                Employee employee = new Employee();
                employee.setEno(8000+i);
                employee.setEname("Mike"+i);
                employee.setSalary(1234f);
                employee.setDname("Boss");
                employee.setHiredate(new Date());
                employeeDao.insert(employee);
            }
            transactionManager.commit(status);
        } catch (TransactionException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }
}
