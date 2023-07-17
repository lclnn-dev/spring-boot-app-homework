package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.request.WorkPlaceRequestDto;
import com.example.demowithtests.dto.response.WorkPlaceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeWorkplaceMapper.class})
public interface WorkPlaceMapper {

    WorkPlaceResponseDto toWorkPlaceResponse(WorkPlace workPlace);

    List<WorkPlaceResponseDto> toWorkPlaceResponseList(List<WorkPlace> workPlaces);

    WorkPlace toWorkPlaceEntity(WorkPlaceRequestDto request);

}
