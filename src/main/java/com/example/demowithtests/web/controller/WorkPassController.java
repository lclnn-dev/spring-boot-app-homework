package com.example.demowithtests.web.controller;

import com.example.demowithtests.dto.request.WorkPassRequestDto;
import com.example.demowithtests.dto.response.WorkPassResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkPassController {

    List<WorkPassResponseDto> getAllPasses();

    WorkPassResponseDto addWorkPass(WorkPassRequestDto passRequest);

    List<WorkPassResponseDto> getAllOldPassesEmployee(Integer employeeId);

    ResponseEntity<String> addPhotoToWorkPass(Long passId, MultipartFile file);
}
