package com.example.demowithtests.web.controller.impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.request.EmployeeUpdateRequestDto;
import com.example.demowithtests.dto.request.WorkPlaceRequestDto;
import com.example.demowithtests.dto.response.EmployeeResponseDto;
import com.example.demowithtests.dto.response.WorkPlaceResponseDto;
import com.example.demowithtests.service.WorkPlaceService;
import com.example.demowithtests.util.mapper.WorkPlaceMapper;
import com.example.demowithtests.web.controller.WorkPlaceController;
import com.example.demowithtests.web.controller.swagger.WorkPlaceControllerSwagger;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkPlaceResponseDto updateById(@PathVariable("id") Long id,
                                           @RequestBody WorkPlaceRequestDto workPlaceRequest) {
        WorkPlace workPlaceEntity = workPlaceMapper.toWorkPlaceEntity(workPlaceRequest);
        WorkPlace updatedWorkPlaceEntity = workPlaceService.updateById(id, workPlaceEntity);

        return workPlaceMapper.toWorkPlaceResponse(updatedWorkPlaceEntity);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        workPlaceService.deleteById(id);
    }

    @Override
    @DeleteMapping("/em/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdEM(@PathVariable Long id) {
        workPlaceService.deleteByIdEM(id);
    }
}
