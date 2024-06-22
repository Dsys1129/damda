package com.damda.global.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile image, ImageFolderEnum imageFolder);
    void delete(String imagePath);
}
