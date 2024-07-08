package com.damda.couple.dto;

import lombok.Getter;

@Getter
public class CoupleSearchResponseDTO {

    private Long id;
    private String image;
    private String name;

    public CoupleSearchResponseDTO(Long id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }
}
