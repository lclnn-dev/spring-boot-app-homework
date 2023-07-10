package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.WorkPassService;
import com.example.demowithtests.util.annotation.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotation.entity.Name;
import com.example.demowithtests.util.annotation.entity.ToLowerCase;
import com.example.demowithtests.util.exception.DataAlreadyExistsException;
import com.example.demowithtests.util.exception.NoResultsFoundException;
import com.example.demowithtests.util.exception.ResourceDeleteStatusException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final WorkPassService passService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEM(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAll(pageable);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", id.toString()));

        if (employee.getIsDeleted()) {
            throw new ResourceWasDeletedException(Employee.class, "id", id.toString());
        }

        return employee;
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", id.toString()));
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", id.toString()));
        employeeRepository.delete(employee);
    }

    @Override
    public void removeSoftById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", id.toString()));

        setEmployeeDeletedStatus(employee, true);
    }

    @Override
    public void recoverById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", id.toString()));

        setEmployeeDeletedStatus(employee, false);
    }

    private void setEmployeeDeletedStatus(Employee employee, boolean isDeleted) {
        if (employee.getIsDeleted() == isDeleted) {
            String status = isDeleted ? "deleted" : "not deleted";
            throw new ResourceDeleteStatusException(Employee.class, "id", employee.getId().toString(), status);
        }

        employee.setIsDeleted(isDeleted);
        employeeRepository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    @Override
    public List<Employee> findAllByCountry(String country) {
        return employeeRepository.findAllByCountry(country);
    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        Page<Employee> resultPage = employeeRepository.findAllByCountryContaining(country, pageable);

        if (resultPage.getTotalElements() == 0) {
            throw new NoResultsFoundException("employees", "country", country);
        }

        return resultPage;
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<Employee> findAllByEmailNull() {
        return employeeRepository.findAllByEmailNull();
    }

    @Override
    public List<Employee> updateAllByCountryFirstCharLowerToUpper() {
        List<Employee> resultEmployees = employeeRepository.findAllByCountryStartsWithLowerCase();

        if (!resultEmployees.isEmpty()) {
            resultEmployees.forEach(e -> e.setCountry(StringUtils.capitalize(e.getCountry())));
            employeeRepository.saveAll(resultEmployees);
        }

        return resultEmployees;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .toList();
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();

        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c != null)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    @Override
    public List<Employee> findAllByCountryNotIn(List<String> countries) {
        return employeeRepository.findAllByCountryNotIn(countries);
    }

    @Override
    public List<Employee> findAllDeletedByIds(List<Integer> ids) {
        return employeeRepository.findAllDeletedByIds(ids);
    }

    @Override
    public Employee handPassportToEmployee(Integer employeeId, Long passId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", employeeId.toString()));

        if (employee.getWorkPass() != null) {
            throw new DataAlreadyExistsException("Employee is already has work pass");
        }

        WorkPass pass = passService.getById(passId);
        handlePassport(employee, pass);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee handFreePassportToEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, "id", employeeId.toString()));

        if (employee.getWorkPass() != null) {
            throw new DataAlreadyExistsException("Employee is already have work pass");
        }

        WorkPass pass = passService.getFirstFreePass();
        handlePassport(employee, pass);

        return employeeRepository.save(employee);
    }

    private void handlePassport(Employee employee, WorkPass pass) {
        if (pass.getIsHanded()) {
            throw new DataAlreadyExistsException("Pass is already handed");
        }

        LocalDateTime createDate = LocalDateTime.now();
        pass.setIssueDate(createDate);
        pass.setExpireDate(createDate.plusMonths(2));
        pass.setIsHanded(true);
        passService.updateById(pass.getId(), pass);
        employee.setWorkPass(pass);
    }
}
