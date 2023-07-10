package com.example.demowithtests.web.controller;

import com.example.demowithtests.dto.response.EmployeeResponseDto;
import com.example.demowithtests.dto.response.PhotoPassResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoController {

    PhotoPassResponseDto getById(Long id);

    List<PhotoPassResponseDto> getAll();
}
