package com.example.demowithtests.service.impl;

import com.example.demowithtests.service.FileStorageService;
import com.example.demowithtests.util.config.PhotoPassConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class FileStorageServiceBean implements FileStorageService {

    private  PhotoPassConfig photoPassConfig;

    @Override
    public String storeFile(MultipartFile file) {
        String fileStorageLocation = photoPassConfig.getStorageDir();
        String filename = file.getOriginalFilename();

        try {
            Path filePath = Paths.get(fileStorageLocation).resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + filename, e);
        }
    }

    @Override
    public byte[] getFile(String url) {
        String fileStorageLocation = photoPassConfig.getStorageDir();

        try {
            Path filePath = Paths.get(fileStorageLocation).resolve(url);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + url, e);
        }
    }
}
