package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

}
