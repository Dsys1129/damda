package com.damda.user.entity;

public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    String desc;

    Gender(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
