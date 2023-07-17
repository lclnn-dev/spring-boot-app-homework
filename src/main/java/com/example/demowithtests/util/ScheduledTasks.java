package com.example.demowithtests.util;

import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.service.impl.EmployeeWorkPlaceServiceBean;
import com.example.demowithtests.util.constant.WorkPlaceConstants;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final EmployeeWorkPlaceServiceBean employeeWorkPlaceServiceBean;

    @Scheduled(cron = WorkPlaceConstants.CRON_ACTIVITY_DATE)
    public List<EmployeeWorkplace> callCheckingActivityOfWorkPlace() {
        return employeeWorkPlaceServiceBean.checkWorkplaceActivityByDate();
    }
}
