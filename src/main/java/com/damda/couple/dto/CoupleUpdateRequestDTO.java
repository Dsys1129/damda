package com.damda.couple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoupleUpdateRequestDTO {
    private String name;
    @JsonProperty(value = "d-day")
    private Integer dDay;
}
