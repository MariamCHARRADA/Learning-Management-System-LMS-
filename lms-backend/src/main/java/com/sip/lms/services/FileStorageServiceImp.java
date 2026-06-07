package com.sip.lms.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImp implements FileStorageService {

    private final Path uploadDir =
            Paths.get(System.getProperty("user.dir"),
            "src/main/resources/static/uploads");

    private static final String DEFAULT_IMAGE = "default.png";

    public FileStorageServiceImp() {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload directory");
        }
    }

    @Override
    public String saveFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return DEFAULT_IMAGE;
        }

        String fileName = generateUniqueName(file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }

        return fileName;
    }

    @Override
    public void deleteFile(String filename) {

        if (filename == null || filename.equals(DEFAULT_IMAGE)) {
            return;
        }

        try {
            Files.deleteIfExists(uploadDir.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("File delete failed: " + e.getMessage());
        }
    }


    private String generateUniqueName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}