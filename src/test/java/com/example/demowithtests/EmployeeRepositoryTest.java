package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Employee Repository Tests")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("Save employee - test")
    void shouldCreateNewEmployee() {

        Employee employee = Employee.builder()
                .name("Mark")
                .country("England")
                .addresses(new HashSet<>(Set.of(
                        Address
                                .builder()
                                .country("UK")
                                .build())))
                .gender(Gender.M)
                .build();

        employeeRepository.save(employee);

        assertThat(employee.getId()).isGreaterThan(0);
        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getName()).isEqualTo("Mark");
        assertThat(employee.getCountry()).isEqualTo("England");

        assertThat(employee.getAddresses()).isNotEmpty();

        Address address = employee.getAddresses().iterator().next();
        assertThat(address.getCountry()).isEqualTo("UK");
        assertThat(address.getId()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("Get all employees - test")
    void shouldFindAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees.size()).isGreaterThan(0);
        assertThat(employees).hasSize(1);
    }

    @Test
    @Order(2)
    @DisplayName("Find employee by id - test")
    void shouldGetEmployeeById() {

        Optional<Employee> employee = employeeRepository.findById(1);

        assertThat(employee).isPresent();
        assertThat(employee.get().getId()).isEqualTo(1);
        assertThat(employee.get().getName()).isEqualTo("Mark");
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    @DisplayName("Update employee - test")
    void shouldUpdateEmployeeById() {

        Employee employee = employeeRepository.findById(1).orElseThrow();
        employee.setName("Martin");
        Employee employeeUpdated = employeeRepository.save(employee);

        assertThat(employeeUpdated.getName()).isEqualTo("Martin");
    }

    @Test
    @Order(3)
    @DisplayName("Find employee by gender and country - test")
    void shouldFindEmployeesByGenderAndCountry() {

        List<Employee> employees = employeeRepository.findAllByGenderAndAddressCountry(Gender.M.toString(), "UK");

        assertThat(employees).isNotNull();
        assertThat(employees.get(0).getGender()).isEqualTo(Gender.M);
    }

    @Test
    @Order(3)
    @DisplayName("Get employees by email is null - test")
    @Rollback(value = false)
    void shouldGetListEmployeesByEmailNull() {

        Employee employee = Employee.builder()
                .name("name1")
                .country("country1")
                .email(null)
                .build();

        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findAllByEmailNull();

        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees).hasSize(2);
    }

    @Test
    @Order(4)
    @DisplayName("Get employees by country starts with lower case - test")
    void shouldGetListEmployeesByCountryStartsWithLowerCase() {

        List<Employee> employees = employeeRepository.findAllByCountryStartsWithLowerCase();

        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees).hasSize(1);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    @DisplayName("Delete employee - test")
    void deleteEmployeeTest() {
        List<Employee> employeesBefore = employeeRepository.findAll();

        Optional<Employee> employee = employeeRepository.findFirstByName("Martin");
        assertThat(employee).isPresent();

        employeeRepository.delete(employee.get());

        List<Employee> employeesAfter = employeeRepository.findAll();
        assertThat(employeesAfter).hasSize(employeesBefore.size() - 1);
    }
}
