package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.EmployeeWorkplace;
import com.example.demowithtests.dto.response.EmployeeWorkPlaceResponseDto;
import com.example.demowithtests.dto.response.WorkPlaceForEmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface EmployeeWorkplaceMapper {

    @Mapping(target = ".", source = "workPlace")
    WorkPlaceForEmployeeResponseDto employeeWorkplaceActivityToWorkPlaceForEmployeeResponseDto(EmployeeWorkplace employeeWorkplace);


    default Set<WorkPlaceForEmployeeResponseDto> employeeWorkplaceActivitySetToWorkPlaceForEmployeeResponseDtoSet(Set<EmployeeWorkplace> set) {
        if (set == null) {
            return null;
        }
        Set<WorkPlaceForEmployeeResponseDto> set1 = new LinkedHashSet<WorkPlaceForEmployeeResponseDto>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (EmployeeWorkplace employeeWorkplace : set) {

            if (Boolean.TRUE.equals(employeeWorkplace.getIsActive())) {
                set1.add(employeeWorkplaceActivityToWorkPlaceForEmployeeResponseDto(employeeWorkplace));
            }
        }

       return set1;
    }

    @Mapping(target = "employeeId", source = "employee.id")
    EmployeeWorkPlaceResponseDto employeeWorkplaceActivityToEmployeeWorkPlaceResponseDto(EmployeeWorkplace employeeWorkplace);

    default Set<EmployeeWorkPlaceResponseDto> employeeWorkplaceActivitySetToEmployeeWorkPlaceResponseDtoSet(Set<EmployeeWorkplace> set) {
        if ( set == null ) {
            return null;
        }

        Set<EmployeeWorkPlaceResponseDto> set1 = new LinkedHashSet<EmployeeWorkPlaceResponseDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( EmployeeWorkplace employeeWorkplace : set ) {
            set1.add( employeeWorkplaceActivityToEmployeeWorkPlaceResponseDto(employeeWorkplace) );
        }

        return set1;
    }
}