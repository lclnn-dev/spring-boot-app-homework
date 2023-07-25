package com.example.demowithtests.util.mapper;

import com.example.demowithtests.dto.response.HistoryPassResponseDto;
import com.example.demowithtests.enumeration.WorkPassAction;
import com.example.demowithtests.util.HistoryWorkPassActions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryPassMapper {

    @Mapping(target = "action", expression = "java(action.toString())")
    HistoryPassResponseDto toHistoryPassResponse(HistoryWorkPassActions.Key key, WorkPassAction action);
}
