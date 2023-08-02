package com.example.demowithtests.web.controller;

import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface EmployeeController {

    List<EmployeeResponseDto> getAllUsers();

    Page<EmployeeResponseDto> getPage(int page, int size);

    EmployeeResponseDto getEmployeeById(Integer id);

    EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequest);

    EmployeeResponseDto refreshEmployee(Integer id, EmployeeUpdateRequestDto employeeRequest);

    void removeSoftEmployeeById(Integer id);

    void recoverEmployeeById(Integer id);

    void removeEmployeeById(Integer id);

    void removeAllUsers();

    Page<EmployeeResponseDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    List<EmployeeResponseDto> getByCountry(String country);

    List<EmployeeResponseDto> getAllByEmailNull();

    List<EmployeeResponseDto> updateAllByCountryFirstCharLowerToUpper();

    List<String> getAllUsersC();

    List<String> getAllUsersSort();

    List<EmployeeResponseDto> getAllByCountryNotIn(List<String> countries);

    List<EmployeeResponseDto> getAllDeletedByIds(List<Integer> ids);

    Set<String> getSetCountries();
}
