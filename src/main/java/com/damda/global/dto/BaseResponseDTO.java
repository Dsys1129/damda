package com.damda.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponseDTO<T> {

    private String message;
    private Integer code;
    private T data;

    private BaseResponseDTO(String message, Integer code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static <T>BaseResponseDTO<T> getBaseResponse201WithData(String message, T data){
        return new BaseResponseDTO<>(message, HttpStatus.CREATED.value(), data);
    }

    public static <T>BaseResponseDTO<T> getBaseResponse200WithData(String message, T data){
        return new BaseResponseDTO<>(message, HttpStatus.OK.value(), data);
    }

    public static BaseResponseDTO<Void> getBaseResponse200WithoutData(String message) {
        return new BaseResponseDTO(message, HttpStatus.OK.value(), null);
    }

    public static BaseResponseDTO<Void> getBaseResponse201WithoutData(String message) {
        return new BaseResponseDTO(message, HttpStatus.CREATED.value(), null);
    }
}
