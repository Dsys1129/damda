package com.damda.couple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoupleCreateRequestDTO {
    private String name;
    @JsonProperty(value = "dDay")
    private Integer dDay;
}