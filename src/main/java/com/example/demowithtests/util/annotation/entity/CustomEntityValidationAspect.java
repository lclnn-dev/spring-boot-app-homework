package com.example.demowithtests.util.annotation.entity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class CustomEntityValidationAspect {

    @Pointcut("@annotation(com.example.demowithtests.util.annotation.entity.ActivateCustomAnnotations) && within(com.example.demowithtests.service.impl.*)")
    public void callAtAnnotationActivator() {
    }

    @Before("callAtAnnotationActivator()")
    public void makeValid(JoinPoint joinPoint) throws IllegalAccessException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ActivateCustomAnnotations annotation = method.getAnnotation(ActivateCustomAnnotations.class);

        List<Class<?>> annotations = Arrays.asList(annotation.value());
        for (Object arg : joinPoint.getArgs()) {
            handleAnnotationsEntity(arg, annotations);
        }
    }

    private void handleAnnotationsEntity(Object argument, List<Class<?>> annotations) throws IllegalAccessException {
        for (Field field : argument.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(argument);
            field.setAccessible(false);

            if (fieldValue instanceof Collection<?>) {
                handleCollectionFieldAnnotations((Collection<?>) fieldValue, annotations);
            } else {
                handleFieldAnnotations(argument, annotations, field);
            }
        }
    }

    private void handleCollectionFieldAnnotations(Collection<?> collection, List<Class<?>> annotations) throws IllegalAccessException {
        for (Object item : collection) {
            handleFieldAnnotations(item, annotations);
        }
    }

    private void handleFieldAnnotations(Object argument, List<Class<?>> annotations) throws IllegalAccessException {
        for (Field field : argument.getClass().getDeclaredFields()) {
            handleFieldAnnotations(argument, annotations, field);
        }
    }

    private void handleFieldAnnotations(Object argument, List<Class<?>> annotations, Field field) throws IllegalAccessException {
        if (shouldSetLowerCase(annotations, field)) {
            setLowerCase(argument, field);
        }
        if (shouldSetFormattedName(annotations, field)) {
            setFormattedName(argument, field);
        }
    }

    private boolean shouldSetLowerCase(List<Class<?>> annotations, Field field) {
        return annotations.contains(ToLowerCase.class) && field.isAnnotationPresent(ToLowerCase.class);
    }

    private boolean shouldSetFormattedName(List<?> annotations, Field field) {
        return annotations.contains(Name.class) && field.isAnnotationPresent(Name.class);
    }

    private void setFormattedName(Object arg, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object valueArg = field.get(arg);
        if (valueArg instanceof String value) {
            field.set(arg, toNameFormat(value));
        }

        field.setAccessible(false);
    }

    private String toNameFormat(String name) {
        return name.trim().substring(0, 1).toUpperCase() + name.trim().substring(1).toLowerCase();
    }

    private void setLowerCase(Object arg, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object valueArg = field.get(arg);
        if (valueArg instanceof String value) {
            field.set(arg, value.toLowerCase());
        }

        field.setAccessible(false);
    }
}
