package com.damda.global.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile image);
    void delete(String imagePath);
}
