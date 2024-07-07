package com.damda.main.dto;

import com.damda.global.config.LawRegionMapper;
import lombok.Getter;

@Getter
public class BestPhotoSpotResponseDTO {
    private Long id;
    private String photoSpotName;
    private String lawRegion;
    private String image;

    public BestPhotoSpotResponseDTO(Long id, String photoSpotName, String lawCode, String image) {
        this.id = id;
        this.photoSpotName = photoSpotName;
        this.lawRegion = LawRegionMapper.convertLawCodeToLawRegion(lawCode);
        this.image = image;
    }
}
