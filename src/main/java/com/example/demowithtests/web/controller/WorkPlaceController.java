package com.example.demowithtests.web.controller;

import com.example.demowithtests.dto.request.WorkPlaceRequestDto;
import com.example.demowithtests.dto.response.WorkPlaceResponseDto;

public interface WorkPlaceController {

    WorkPlaceResponseDto getById(Long id);

    WorkPlaceResponseDto create(WorkPlaceRequestDto workPlaceRequest);
}
