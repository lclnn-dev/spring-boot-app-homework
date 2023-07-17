package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeWorkplaceKey;
import com.example.demowithtests.dto.request.EmployeeRequestDto;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeWorkplaceMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "workPass.employee.id", target = "workPass.employeeId")
    EmployeeResponseDto toEmployeeResponse(Employee employee);

    List<EmployeeResponseDto> toEmployeeResponseList(List<Employee> employees);

    Employee toEmployeeEntity(EmployeeRequestDto request);

    Employee toEmployeeEntity(EmployeeUpdateRequestDto request);

    default Long map(EmployeeWorkplaceKey value) {
        return value.getWorkPlaceId();
    }
}
