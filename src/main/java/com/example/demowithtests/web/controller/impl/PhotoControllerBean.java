package com.example.demowithtests.web.controller.impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.PhotoPass;
import com.example.demowithtests.dto.response.PhotoPassResponseDto;
import com.example.demowithtests.service.PhotoPassService;
import com.example.demowithtests.util.mapper.PhotoPassMapper;
import com.example.demowithtests.web.controller.PhotoController;
import com.example.demowithtests.web.controller.swagger.PhotoPassPassControllerSwagger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@AllArgsConstructor
public class PhotoControllerBean implements PhotoController, PhotoPassPassControllerSwagger {

    private final PhotoPassService photoService;
    private final PhotoPassMapper photoMapper;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoPassResponseDto getById(@PathVariable Long id) {
        PhotoPass photoPass = photoService.getById(id);
        return photoMapper.toPhotoPassResponse(photoPass);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoPassResponseDto> getAll() {
        List<PhotoPass> photos = photoService.getAll();
        return photoMapper.toPhotoPassResponseList(photos);
    }


    @GetMapping(
            value = "/read/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] readPhotoPassById(@PathVariable Long id)  {
        return photoService.readPhotoPassById(id);
    }
}
