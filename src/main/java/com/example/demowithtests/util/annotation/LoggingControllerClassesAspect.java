package com.example.demowithtests.util.annotation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
@Aspect
@Component
public class LoggingControllerClassesAspect {

    @Pointcut("execution(public * com.example.demowithtests.web.EmployeeController.*(..))")
    public void callAtMyControllersPublicMethods() {
    }

    @Before("callAtMyControllersPublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("\u001B[32m" + "Controller - request: {} {}" + "\u001B[0m", request.getMethod(), request.getRequestURI());

        String methodName = joinPoint.getSignature().toShortString();
        log.info("\u001B[32m" + "Controller: " + methodName + " - start." + "\u001B[0m");

    }

    @AfterReturning(value = "callAtMyControllersPublicMethods()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("\u001B[32m" + "Controller: " + methodName + " - end." + "\u001B[0m");
    }
}