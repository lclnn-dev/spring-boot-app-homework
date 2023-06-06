package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByCountry(String country);

    @Query(value = "SELECT * FROM users JOIN addresses ON users.id = addresses.employee_id " +
            "WHERE users.gender = :gender AND addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    Employee findFirstByName(String name);

    Employee findEmployeeByEmailNotNull();

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findFirstByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

}
