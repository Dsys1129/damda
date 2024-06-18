package com.damda.global.image;

import com.damda.global.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LocalStorageImageUploader implements ImageUploader {

    @Value("${image.base.uri}")
    private String BASE_URI;
    private final ImageValidator imageValidator;

    @Override
    public String upload(MultipartFile image, ImageFolderEnum imageFolder) {
        imageValidator.validateImageFile(image);

        String originalFilename = image.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        save(image, storeFileName, imageFolder);
        return storeFileName;
    }

    @Override
    public void delete(String imagePath) {

    }

    private void save(MultipartFile image, String storeFileName, ImageFolderEnum imageFolder) {
        try {
            String folderPath = BASE_URI + imageFolder.getFolderName();
            Path uploadPath = Paths.get(folderPath);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            File dest = new File(folderPath + File.separator + storeFileName);
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("이미지 저장에 실패하였습니다.");
        }
    }
    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String fileName = extractFileName(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return fileName + uuid + "." + ext;
    }

    // 파일명의 확장자 파싱
    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos + 1);
        return ext;
    }

    private String extractFileName(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String fileName = originalFileName.substring(0, pos);
        return fileName;
    }
}