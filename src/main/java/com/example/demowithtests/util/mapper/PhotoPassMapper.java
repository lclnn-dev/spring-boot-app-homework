package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.PhotoPass;
import com.example.demowithtests.dto.response.PhotoPassResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoPassMapper {

    @Mapping(source = "pass.id", target = "workPassId")
    @Mapping(source = "pass.employee.id", target = "employeeId")
    PhotoPassResponseDto toPhotoPassResponse(PhotoPass photoPass);

    List<PhotoPassResponseDto> toPhotoPassResponseList(List<PhotoPass> passes);
}
