package com.db.gerasin.testframework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Before("execution(* com.db.gerasin.testframework.mbean.CommandService.*(..))")
    public void beforeSayMethods(JoinPoint jp) {
        log.info(jp.getTarget().getClass().getSimpleName() + ": " + jp.getSignature().getName() + " invoked");
    }
}
