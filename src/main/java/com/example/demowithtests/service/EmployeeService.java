package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Employee create(Employee employee);

    Employee createEM(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeSoftById(Integer id);

    void removeAll();

    void recoverById(Integer id);

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    List<Employee> findAllByCountry(String country);

    List<Employee> findAllByEmailNull();

    List<Employee> updateAllByCountryFirstCharLowerToUpper();

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    List<Employee> findAllByCountryNotIn(List<String> countries);

    List<Employee> findAllDeletedByIds(List<Integer> ids);

    Employee handPassportToEmployee(Integer employeeId, Long passId);

    Employee handFreePassportToEmployee(Integer employeeId);

    Employee addWorkPlace(Integer employeeId, Long workPlaceId);
}
