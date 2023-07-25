package com.example.demowithtests.service;

import com.example.demowithtests.domain.WorkPass;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkPassService {

    WorkPass create(WorkPass pass);

    WorkPass getById(Long id);

    List<WorkPass> getAll();

    WorkPass updateById(Long passId, WorkPass pass);

    WorkPass getFirstFreePass();

    void addPhotoToWorkPass(Long passId, MultipartFile file);

    List<WorkPass> getAllOldPassesEmployee(Integer employeeId);
}
