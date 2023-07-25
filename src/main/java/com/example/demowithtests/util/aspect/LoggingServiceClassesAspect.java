package com.example.demowithtests.util.aspect;

import com.example.demowithtests.constant.LogColorConstants;
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
            log.debug(LogColorConstants.ANSI_BLUE + "\nService: " + methodName + " - start. Args count - {}:" +
                    "\n{} = {}" + LogColorConstants.ANSI_RESET, args.length, paramNames, args);
        } else {
            log.debug(LogColorConstants.ANSI_BLUE + "Service: " + methodName + " - start." + LogColorConstants.ANSI_RESET);
        }
    }

    @AfterReturning(value = "callAtMyServicesPublicMethods()",
            returning = "returningValue")
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
            log.debug(LogColorConstants.ANSI_BLUE + "Service: " + methodName + " - end.\nReturns - {}" + LogColorConstants.ANSI_RESET, outputValue);
        } else {
            log.debug(LogColorConstants.ANSI_BLUE + "Service: " + methodName + " - end." + LogColorConstants.ANSI_RESET);
        }
    }
}
