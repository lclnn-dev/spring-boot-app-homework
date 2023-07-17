package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.domain.WorkPlace;

import java.util.List;

public interface EmployeeWorkPlaceService {

    EmployeeWorkplace takePlace(Employee employee, WorkPlace workPlace);

    List<EmployeeWorkplace> checkWorkplaceActivityByDate();
}
