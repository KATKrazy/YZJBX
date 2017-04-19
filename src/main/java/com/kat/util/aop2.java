package com.kat.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

public class aop2 {

    public aop2() {
        System.out.println("aop");

    }

    public void aoppoint() {

    }

    public void bef() {
        System.out.println("before");
    }
}
