package com.example.demowithtests.util.annotation;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Log4j2
@Aspect
@Component
public class LoggingServiceClassesAspect {

    @Pointcut("execution(public * com.example.demowithtests.service.impl.EmployeeServiceBean.*(..))")
    public void callAtMyServicesPublicMethods() {
    }

    @Before("callAtMyServicesPublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.toShortString();
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            String paramNames = Arrays.toString(signature.getParameterNames());
            log.debug("\u001B[34m" + "\nService: " + methodName + " - start. Args count - {}:" +
                    "\n{} = {}" + "\u001B[0m", args.length, paramNames, args);
        } else {
            log.debug("\u001B[34m" + "Service: " + methodName + " - start." + "\u001B[0m");
        }
    }

    @AfterReturning(value = "callAtMyServicesPublicMethods()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        String methodName = joinPoint.getSignature().toShortString();
        Object outputValue;
        if (returningValue != null) {
            if (returningValue instanceof Collection) {
                outputValue = "Collection size - " + ((Collection<?>) returningValue).size();
            } else if (returningValue instanceof byte[]) {
                outputValue = "File as byte[]";
            } else {
                outputValue = returningValue;
            }
            log.debug("\u001B[34m" + "Service: " + methodName + " - end.\nReturns - {}" + "\u001B[0m", outputValue);
        } else {
            log.debug("\u001B[34m" + "Service: " + methodName + " - end." + "\u001B[0m");
        }
    }
}
