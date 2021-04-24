package com.example.spring.aop.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyInvocationHandler implements InvocationHandler {
    private Object target;
    private ProxyInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
        Object ret = method.invoke(target, args);
        return ret;
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProxyInvocationHandler invocationHandler = new ProxyInvocationHandler(userService);
        UserService userServiceProxy = (UserService)Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                invocationHandler);
        userServiceProxy.createUser();

        EmployeeService employeeService = new EmployeeServiceImpl();
        EmployeeService employeeServiceProxy = (EmployeeService)Proxy.newProxyInstance(employeeService.getClass().getClassLoader(),
                employeeService.getClass().getInterfaces(),
                new ProxyInvocationHandler(employeeService));
        employeeServiceProxy.createEmployee();

    }
}
