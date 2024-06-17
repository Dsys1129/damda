package com.damda.user.dto;

import lombok.Getter;

@Getter
public class UserSignupRequestDTO {
    private String userId;
    private String password;
    private String nickname;
    private Integer age;
    private String gender;
}
