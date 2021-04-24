package com.example.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodAspect {
    public void printExecutionTime(JoinPoint joinPoint){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String now = sdf.format(new Date());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("--->"+now + ":"+className+" "+methodName);
        Object[] args = joinPoint.getArgs();
        System.out.println(args.length);
        for (Object arg:args){
            System.out.println("--->"+arg);
        }


    }

    public void doAfter(JoinPoint joinPoint){
        System.out.println("<---doAfter");

    }

    public void doAfterReturning(JoinPoint joinPoint,Object ret){
        System.out.println("<---doAfterReturning: "+ret);
    }

    public void doAfterThrowing(JoinPoint joinPoint,Throwable th){
        System.out.println("<---doAfterThrowing: "+ th.getMessage());
    }
}
