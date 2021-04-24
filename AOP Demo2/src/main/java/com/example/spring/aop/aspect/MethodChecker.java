package com.example.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodChecker {
    public Object check(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = new Date().getTime();
            Object ret = pjp.proceed();
            long endTime = new Date().getTime();
            long duration = endTime - startTime;
            if (duration>=1000){
                String className = pjp.getTarget().getClass().getName();
                String methodName = pjp.getSignature().getName();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                String now = sdf.format(new Date());
                System.out.println(now + ": "+className+"."+methodName+" "+duration);

            }
            return ret;
        } catch (Throwable throwable) {
            System.out.println("Exception: "+throwable.getMessage());
            throw throwable;
        }


    }
}
