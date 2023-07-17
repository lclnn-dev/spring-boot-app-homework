package com.example.demowithtests.web.controller.impl;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.request.WorkPlaceRequestDto;
import com.example.demowithtests.dto.response.WorkPlaceResponseDto;
import com.example.demowithtests.service.WorkPlaceService;
import com.example.demowithtests.util.mapper.WorkPlaceMapper;
import com.example.demowithtests.web.controller.WorkPlaceController;
import com.example.demowithtests.web.controller.swagger.WorkPlaceControllerSwagger;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workplaces")
@AllArgsConstructor
public class WorkPlaceControllerBean implements WorkPlaceController, WorkPlaceControllerSwagger {

    private final WorkPlaceService workPlaceService;
    private final WorkPlaceMapper workPlaceMapper;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkPlaceResponseDto getById(@PathVariable Long id) {
        WorkPlace workPlace = workPlaceService.getById(id);
        return workPlaceMapper.toWorkPlaceResponse(workPlace);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkPlaceResponseDto create(@RequestBody @Valid WorkPlaceRequestDto workPlaceRequest) {
        WorkPlace workPlace = workPlaceMapper.toWorkPlaceEntity(workPlaceRequest);
        WorkPlace savedWorkPlace = workPlaceService.create(workPlace);

        return workPlaceMapper.toWorkPlaceResponse(savedWorkPlace);
    }
}
