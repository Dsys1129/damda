package com.damda.global.image;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalStorageImageUploader implements ImageUploader {
    @Override
    public String upload(MultipartFile image) {
        return null;
    }

    @Override
    public void delete(String imagePath) {

    }
}
