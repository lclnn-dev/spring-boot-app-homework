package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.domain.EmployeeWorkplaceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeWorkplaceRepository extends JpaRepository<EmployeeWorkplace, EmployeeWorkplaceKey> {

    Integer countByEmployee(Employee employee);

    List<EmployeeWorkplace> findAllByEmployee(Employee employee);

    List<EmployeeWorkplace> findByEndDateBeforeAndIsActiveTrue(LocalDateTime currentDate);

}
