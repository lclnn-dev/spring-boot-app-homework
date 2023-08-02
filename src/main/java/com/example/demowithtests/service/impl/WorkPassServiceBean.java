package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.PhotoPass;
import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.repository.WorkPassRepository;
import com.example.demowithtests.service.FileStorageService;
import com.example.demowithtests.service.WorkPassService;
import com.example.demowithtests.util.config.PhotoPassConfig;
import com.example.demowithtests.util.exception.DataAlreadyExistsException;
import com.example.demowithtests.util.exception.NoResultsFoundException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkPassServiceBean implements WorkPassService {

    private final WorkPassRepository passRepository;
    private final FileStorageService fileStorageService;
    private final PhotoPassConfig photoPassConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<WorkPass> getAll() {
        return passRepository.findAll().stream()
                .sorted(Comparator.comparing(WorkPass::getId))
                .toList();
    }

    @Override
    public WorkPass create(WorkPass pass) {
        return passRepository.save(pass);
    }

    @Override
    public WorkPass getById(Long id) {
        WorkPass pass = passRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WorkPass.class, "id", id.toString()));

        if (pass.getIsDeleted() != null && pass.getIsDeleted()) {
            throw new ResourceWasDeletedException(WorkPass.class, "id", id.toString());
        }

        return pass;
    }

    @Override
    public WorkPass updateById(Long passId, WorkPass pass) {

        WorkPass existingPass = passRepository.findById(passId)
                .orElseThrow(() -> new ResourceNotFoundException(WorkPass.class, "id", passId.toString()));

        boolean isUpdated = false;

        if (pass.getIsHanded() != null && !pass.getIsHanded().equals(existingPass.getIsHanded())) {
            existingPass.setIsHanded(pass.getIsHanded());
            isUpdated = true;
        }
        if (pass.getIsDeleted() != null && !pass.getIsDeleted().equals(existingPass.getIsDeleted())) {
            existingPass.setIsDeleted(pass.getIsDeleted());
            isUpdated = true;
        }
        if (pass.getExpireDate() != null && !pass.getExpireDate().equals(existingPass.getExpireDate())) {
            existingPass.setExpireDate(pass.getExpireDate());
            isUpdated = true;
        }
        if (pass.getCancelDate() != null && !pass.getCancelDate().equals(existingPass.getCancelDate())) {
            existingPass.setCancelDate(pass.getCancelDate());
            isUpdated = true;
        }
        if (pass.getPrevEmployeeId() != null && !pass.getPrevEmployeeId().equals(existingPass.getPrevEmployeeId())) {
            existingPass.setPrevEmployeeId(pass.getPrevEmployeeId());
            isUpdated = true;
        }

        if (isUpdated) {
            return passRepository.save(existingPass);
        } else {
            return existingPass;
        }

    }

    @Transactional
    public WorkPass updateExpireDateEM(Long passId, WorkPass pass) {
        WorkPass existingPass = entityManager.find(WorkPass.class, passId);

        if (existingPass == null) {
            throw new ResourceNotFoundException(WorkPass.class, "id", passId.toString());
        }
        existingPass.setExpireDate(pass.getExpireDate());

        return entityManager.merge(existingPass);
    }

    @Override
    public WorkPass getFirstFreePass() {
        return passRepository.findAll()
                .stream()
                .filter(s -> !s.getIsHanded() && !s.getIsDeleted())
                .findFirst()
                .orElseThrow(() -> new NoResultsFoundException("workpasses", "handed", String.valueOf(false)));
    }

    @Override
    public void addPhotoToWorkPass(Long passId, MultipartFile file) {
        WorkPass workPass = passRepository.findById(passId)
                .orElseThrow(() -> new ResourceNotFoundException(WorkPass.class, "id", passId.toString()));

        if (!workPass.getIsHanded()) {
            throw new IllegalArgumentException("Pass not handed with any employee");
        }

        if (workPass.getPhoto() != null) {
            throw new DataAlreadyExistsException("Pass already have a photo");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Uploaded file is not an image");
        }

        String filename = fileStorageService.storeFile(file);

        PhotoPass photo = new PhotoPass();
        photo.setFileName(filename);
        photo.setUrl(photoPassConfig.getStorageDir() + "/" + filename);

        workPass.setPhoto(photo);
        passRepository.save(workPass);
    }

    @Override
    public List<WorkPass> getAllOldPassesEmployee(Integer employeeId) {
        return passRepository.findAllByPrevEmployeeId(employeeId);
    }


}

