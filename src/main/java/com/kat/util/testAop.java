package com.kat.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
public class testAop {

    public testAop() {
        System.out.println("testAop");

    }

    //拦截所有公共方法
    @Pointcut("execution(public * *(..))")
    public void aoppoint() {

    }

    @Before("aoppoint()")
    public void before() {
        System.out.println("before");
    }
}
