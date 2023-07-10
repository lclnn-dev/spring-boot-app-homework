package com.example.demowithtests.service;

import com.example.demowithtests.domain.PhotoPass;

import java.util.List;

public interface PhotoPassService {

    PhotoPass getById(Long id);

    List<PhotoPass> getAll();

    byte[] readPhotoPassById(Long id);
}
