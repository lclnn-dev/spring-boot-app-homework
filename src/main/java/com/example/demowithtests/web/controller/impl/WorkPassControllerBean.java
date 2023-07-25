package com.example.demowithtests.web.controller.impl;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.dto.request.WorkPassRequestDto;
import com.example.demowithtests.dto.response.WorkPassResponseDto;
import com.example.demowithtests.service.WorkPassService;
import com.example.demowithtests.util.mapper.WorkPassMapper;
import com.example.demowithtests.web.controller.WorkPassController;
import com.example.demowithtests.web.controller.swagger.WorkPassControllerSwagger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/workpasses")
@AllArgsConstructor
public class WorkPassControllerBean implements WorkPassController, WorkPassControllerSwagger {

    private final WorkPassService passService;
    private final WorkPassMapper passMapper;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkPassResponseDto> getAllPasses() {
        List<WorkPass> passes = passService.getAll();
        return passMapper.toWorkPassResponseList(passes);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkPassResponseDto addWorkPass(@RequestBody WorkPassRequestDto passRequest) {
        WorkPass pass = passMapper.toWorkPassEntity(passRequest);
        WorkPass savedPass = passService.create(pass);

        return passMapper.toWorkPassResponse(savedPass);
    }

    @Override
    @PostMapping("/{passId}/photos")
    public ResponseEntity<String> addPhotoToWorkPass(
            @PathVariable("passId") Long passId,
            @RequestParam("file") MultipartFile file
    ) {
        passService.addPhotoToWorkPass(passId, file);
        return ResponseEntity.ok("Photo added to WorkPass successfully");
    }

    @Override
    @GetMapping("/old-passes/{employeeId}")
    public List<WorkPassResponseDto> getAllOldPassesEmployee(@PathVariable Integer employeeId) {
        List<WorkPass> workPasses = passService.getAllOldPassesEmployee(employeeId);
        return passMapper.toWorkPassResponseList(workPasses);

    }
}
