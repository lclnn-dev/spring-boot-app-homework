package com.example.demowithtests.util.aspect;

import com.example.demowithtests.util.constant.LogColorConstants;
import com.example.demowithtests.util.exception.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
@Aspect
@Component
public class LoggingHandlerExceptionAspect {

    @Around("execution(* (@org.springframework.web.bind.annotation.ControllerAdvice *).*(..))")
    public Object logExceptionHandling(ProceedingJoinPoint joinPoint) throws Throwable {
        String nameException = "";
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Throwable) {
                nameException = arg.getClass().getSimpleName();
                break;
            }
        }
        log.info(LogColorConstants.ANSI_YELLOW + "Exception: {}" + LogColorConstants.ANSI_RESET, nameException);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(LogColorConstants.ANSI_YELLOW + "Request: {} {}" + LogColorConstants.ANSI_RESET, request.getMethod(), request.getRequestURI());

        Object result = joinPoint.proceed();
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object responseBody = responseEntity.getBody();

            if (responseBody instanceof ErrorDetails errorDetails) {
                String message = errorDetails.getMessage();
                String details = errorDetails.getDetails();

                if (details != null) {
                    message += " " + details;
                }
                log.info(LogColorConstants.ANSI_YELLOW + "Response: Status {} - Message: {}" + LogColorConstants.ANSI_RESET, responseEntity.getStatusCode(), message);
            }
        }

        return result;
    }
}
