package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.impl.EmployeeServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceBean employeeServiceBean;

    private Employee employee;
    private Employee employeeDeleted;

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Mark")
                .country("UK")
                .email("test@mail.com")
                .gender(Gender.M)
                .build();

        employeeDeleted = Employee
                .builder()
                .id(2)
                .name("name2")
                .country("country2")
                .email("email2@mail.com")
                .gender(Gender.F)
                .isDeleted(true)
                .build();
    }

    @Test
    @DisplayName("Save employee - test")
    void whenSaveEmployee_shouldReturnEmployee() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeServiceBean.create(employee);

        assertThat(createdEmployee.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Get employee by exist id - test")
    void whenGivenId_shouldReturnEmployee_ifFound() {

        Employee employee = new Employee();
        employee.setId(88);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee expected = employeeServiceBean.getById(employee.getId());

        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Throw exception when employee not found - test")
    void whenEmployeeDoesntExist_shouldThrowResourceNotFoundException() {

        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(5));
    }

    @Test
    @DisplayName("Throw exception when employee mark delete - test")
    void whenEmployeeMarkDelete_shouldThrowResourceWasDeletedException() {

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employeeDeleted));
        assertThrows(ResourceWasDeletedException.class, () -> employeeServiceBean.getById(2));
        verify(employeeRepository, times(1)).findById(2);
    }

    @Test
    @DisplayName("Get employee by id - test")
    void shouldFindEmployeeById() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeServiceBean.getById(employee.getId());

        assertThat(foundEmployee).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Get all employees - test")
    void shouldFindAllEmployees() {

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Update employee by id - test")
    void shouldUpdateExistEmployeeById_returnUpdatedEmployee() {

        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("New Name");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeServiceBean.updateById(1, updatedEmployee);

        assertEquals(updatedEmployee, result);
        assertEquals("New Name", employee.getName());

        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("Throw exception when update employee by id non exist - test")
    void whenUpdateNonExistEmployee_shouldThrowResourceNotFoundException() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("New Name");

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeServiceBean.updateById(1, updatedEmployee));
        verify(employeeRepository, Mockito.times(1)).findById(1);
        verify(employeeRepository, never()).save(Mockito.any(Employee.class));
    }

    @Test
    @DisplayName("Delete employee by id - test")
    void shouldDeleteEmployeeById() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employeeServiceBean.removeById(employee.getId());

        verify(employeeRepository).delete(employee);
    }

    @Test
    @DisplayName("Get all employees with email is null - test")
    void shouldFindAllByEmailNull() {

        employee.setEmail(null);
        List<Employee> employees = List.of(employee);

        when(employeeRepository.findAllByEmailNull()).thenReturn(employees);

        List<Employee> result = employeeServiceBean.findAllByEmailNull();

        assertEquals(1, result.size());
        assertTrue(result.contains(employee));

        verify(employeeRepository, times(1)).findAllByEmailNull();
    }

    @Test
    @DisplayName("Update all employees with first char lower to upper - test")
    void shouldUpdateAllByCountryFirstCharLower_returnListWithUpperChar() {
        List<Employee> employees = List.of(employeeDeleted);

        when(employeeRepository.findAllByCountryStartsWithLowerCase()).thenReturn(employees);
        when(employeeRepository.saveAll(employees)).thenReturn(employees);

        List<Employee> result = employeeServiceBean.updateAllByCountryFirstCharLowerToUpper();

        assertEquals(employees, result);
        assertEquals("Country2", employeeDeleted.getCountry());

        verify(employeeRepository, times(1)).findAllByCountryStartsWithLowerCase();
        verify(employeeRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Return empty list when non exist employees with first char lower - test")
    void shouldReturnEmptyList_whenUpdateAllByCountryFirstCharLowerToUpperWithoutEmployees() {
        List<Employee> emptyEmployees = Collections.emptyList();

        when(employeeRepository.findAllByCountryStartsWithLowerCase()).thenReturn(emptyEmployees);

        List<Employee> result = employeeServiceBean.updateAllByCountryFirstCharLowerToUpper();

        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findAllByCountryStartsWithLowerCase();
        verify(employeeRepository, never()).saveAll(Mockito.anyList());
    }
}
