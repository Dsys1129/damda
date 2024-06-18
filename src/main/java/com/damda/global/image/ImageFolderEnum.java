package com.damda.global.image;

public enum ImageFolderEnum {

    FEED("feeds"),
    USER("users"),
    COUPLE("couples");

    String folderName;

    ImageFolderEnum(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
