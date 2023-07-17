package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.EmployeeWorkplaceRepository;
import com.example.demowithtests.service.EmployeeWorkPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeWorkPlaceServiceBean implements EmployeeWorkPlaceService {

    private final EmployeeWorkplaceRepository repository;


    @Override
    public EmployeeWorkplace takePlace(Employee employee, WorkPlace workPlace) {
        EmployeeWorkplace activity = new EmployeeWorkplace();
        activity.setEmployee(employee);
        activity.setWorkPlace(workPlace);

        LocalDateTime startDate = LocalDateTime.now();
        activity.setStartDate(startDate);
        activity.setEndDate(startDate.plusMonths(2));

        return repository.save(activity);
    }

    @Override
    public List<EmployeeWorkplace> checkWorkplaceActivityByDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<EmployeeWorkplace> workPlacesExpired = repository.findByEndDateBeforeAndIsActiveTrue(currentDate);

        for (EmployeeWorkplace workplace : workPlacesExpired) {
            workplace.setIsActive(false);
            repository.save(workplace);
        }

        return workPlacesExpired;
    }
}
