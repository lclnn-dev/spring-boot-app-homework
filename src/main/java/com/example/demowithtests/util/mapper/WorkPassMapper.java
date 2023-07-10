package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.dto.request.WorkPassRequestDto;
import com.example.demowithtests.dto.response.WorkPassResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkPassMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    WorkPassResponseDto toWorkPassResponse(WorkPass workPass);

    List<WorkPassResponseDto> toWorkPassResponseList(List<WorkPass> workPasses);

    WorkPass toWorkPassEntity(WorkPassRequestDto request);
}
