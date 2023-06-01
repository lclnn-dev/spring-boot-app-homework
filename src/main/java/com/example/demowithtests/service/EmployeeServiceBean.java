package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {

        return repository.findAll()
                .stream()
                .filter(e -> !e.isDeleted())
                .toList();
    }

    @Override
    public Employee getById(Integer id) {

        return repository.findById(id)
                .filter(e -> !e.isDeleted())
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {

        return repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        employee.setDeleted(true);
        repository.save(employee);
    }

    @Override
    public void removeAll() {
        getAll()
                .forEach(e -> {
                    e.setDeleted(true);
                    repository.save(e);
                });
    }
}
