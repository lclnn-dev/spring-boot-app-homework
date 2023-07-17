package com.example.demowithtests.util.aspect;

import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.util.constant.LogColorConstants;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Log4j2
@Aspect
@Component
public class LoggingScheduledTasksAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled))")
    public void callAtAnnotationActivator() {
    }

    @Before("callAtAnnotationActivator()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Scheduled annotation = method.getAnnotation(Scheduled.class);

        log.debug(LogColorConstants.ANSI_CYAN + "ScheduledTasks: " + method.getName() +
                " - start." + LogColorConstants.ANSI_RESET);
        log.debug(LogColorConstants.ANSI_CYAN + "cronExpression: " + annotation.cron() +
                ". Current dateTime: " + LocalDateTime.now().withNano(0) + LogColorConstants.ANSI_RESET);

    }

    @AfterReturning(value = "callAtAnnotationActivator()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        String methodName = joinPoint.getSignature().toShortString();
        Object outputValue;
        if (returningValue != null) {
            if (returningValue instanceof Collection<?> collection) {
                outputValue = "Collection size - " + collection.size();
                log.debug(LogColorConstants.ANSI_CYAN + "Returns - {}" + LogColorConstants.ANSI_RESET, outputValue);
                for (Object item : collection) {
                    if (item instanceof EmployeeWorkplace employeeWorkplace) {
                        log.debug(LogColorConstants.ANSI_CYAN + "Collection item. employeeId: {}, workPlaceId: {}, endTime: {}" + LogColorConstants.ANSI_RESET,
                                employeeWorkplace.getEmployee().getId(), employeeWorkplace.getWorkPlace().getId(), employeeWorkplace.getEndDate());
                    }
                }

            }
        }
        log.debug(LogColorConstants.ANSI_CYAN + "ScheduledTasks: " + methodName + " - end." + LogColorConstants.ANSI_RESET);
    }
}
