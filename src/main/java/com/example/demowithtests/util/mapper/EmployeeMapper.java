package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeResponseDto toEmployeeResponse(Employee employee);

    List<EmployeeResponseDto> toEmployeeResponseList(List<Employee> employees);

    Employee toEmployeeEntity(EmployeeRequestDto request);

    Employee toEmployeeEntity(EmployeeUpdateRequestDto request);
}
