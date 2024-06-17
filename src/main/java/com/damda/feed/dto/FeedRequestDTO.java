package com.damda.feed.dto;

import lombok.Getter;

@Getter
public class FeedRequestDTO {
    private String contents;
    private String contentsDate;
    private String photoSpotName;
    private String lawCode;
    private String latitude;
    private String longitude;
    private String address;
}
