package com.example.demowithtests.web.controller;

import com.example.demowithtests.dto.request.WorkPlaceRequestDto;
import com.example.demowithtests.dto.response.WorkPlaceResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface WorkPlaceController {

    WorkPlaceResponseDto getById(Long id);

    WorkPlaceResponseDto create(WorkPlaceRequestDto workPlaceRequest);

    WorkPlaceResponseDto updateById(Long id, WorkPlaceRequestDto requestDto);

    void deleteById(Long id);
}
