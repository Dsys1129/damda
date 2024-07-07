package com.damda.main.dto;

import lombok.Getter;

@Getter
public class BestCoupleResponseDTO {
    private Long id;
    private String coupleImage;
    private String coupleName;

    public BestCoupleResponseDTO(Long id, String coupleImage, String coupleName) {
        this.id = id;
        this.coupleImage = coupleImage;
        this.coupleName = coupleName;
    }
}
