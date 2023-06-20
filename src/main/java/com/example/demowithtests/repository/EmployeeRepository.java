package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByCountry(String country);

    @Query(value = "SELECT u.* FROM users u JOIN addresses a ON u.id = a.employee_id " +
            "WHERE u.gender = :gender AND a.country = :country", nativeQuery = true)
    List<Employee> findAllByGenderAndAddressCountry(String gender, String country);

    Optional<Employee> findFirstByName(String name);

    List<Employee> findAllByEmailNull();

    @Query(value = "SELECT * FROM users WHERE SUBSTRING(country, 1, 1) = LOWER(SUBSTRING(country, 1, 1))",
            nativeQuery = true)
    List<Employee> findAllByCountryStartsWithLowerCase();

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findAllByName(String name, Pageable pageable);

    Page<Employee> findAllByCountryContaining(String country, Pageable pageable);

    @Query(value = "SELECT e FROM Employee e WHERE e.country NOT IN :countries")
    List<Employee> findAllByCountryNotIn(@Param("countries") List<String> countries);

    @Query(value = "SELECT e FROM Employee e WHERE e.isDeleted = TRUE AND e.id IN :ids")
    List<Employee> findAllDeletedByIds(@Param("ids") List<Integer> ids);
}
