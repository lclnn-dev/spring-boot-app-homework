package com.example.demowithtests.util.aspect;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.enumeration.WorkPassAction;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.HistoryWorkPassActions;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class HistoryWorkPassActionsAspect {

    private final EmployeeService employeeService;

    @Pointcut("execution(public * com.example.demowithtests.service.impl.EmployeeServiceBean.cancelPassFromEmployee(Integer))" +
            " && args(employeeId)")
    public void cancelPassFromEmployeePointcut(Integer employeeId) {
    }

    @Pointcut("execution(public * com.example.demowithtests.service.impl.EmployeeServiceBean.handPassToEmployee(Integer, Long))" +
            " && args(employeeId, passId)")
    public void handPassToEmployeePointcut(Integer employeeId, Long passId) {
    }

    @After("handPassToEmployeePointcut(employeeId, passId)")
    public void afterHandPassToEmployee(JoinPoint joinPoint, Integer employeeId, Long passId) {
        HistoryWorkPassActions.addAction(WorkPassAction.HANDED, employeeId, passId);
    }

    @Around("cancelPassFromEmployeePointcut(employeeId)")
    public void aroundCancelPassFromEmployee(ProceedingJoinPoint joinPoint, Integer employeeId) throws Throwable {
        Employee employee = employeeService.getById(employeeId);
        WorkPass currentPass = employee.getWorkPass();
        if (currentPass != null) {
            joinPoint.proceed();
            HistoryWorkPassActions.addAction(WorkPassAction.DEPRIVED, employeeId, currentPass.getId());
        }
    }
}
