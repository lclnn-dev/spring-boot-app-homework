package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.PhotoPass;
import com.example.demowithtests.util.repository.PhotoPassRepository;
import com.example.demowithtests.service.FileStorageService;
import com.example.demowithtests.service.PhotoPassService;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhotoPassServiceBean implements PhotoPassService {

    private final PhotoPassRepository photoRepository;
    private final FileStorageService fileStorageService;

    @Override
    public PhotoPass getById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PhotoPass.class, "id", id.toString()));
    }

    @Override
    public List<PhotoPass> getAll() {
        return photoRepository.findAll();
    }

    @Override
    public byte[] readPhotoPassById(Long id) {
        PhotoPass photoPass = photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PhotoPass.class, "id", id.toString()));

        return fileStorageService.getFile(photoPass.getUrl());
    }
}
