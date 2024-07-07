package com.damda.feed.dto;

import com.damda.feed.entity.Feed;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class FeedDetailResponseDTO {

    private Long id;
    private String image;
    private String contents;
    private LocalDate contentsDate;
    private String photoSpotName;
    private String address;
    private String lawRegion;
    private String latitude;
    private String longitude;
    private LocalDateTime createdAt;
    private boolean liked = false;
    private String coupleImage;
    private String coupleName;

    public FeedDetailResponseDTO(Feed feed) {
        this.id = feed.getId();
        this.image = feed.getImage();
        this.contents = feed.getContents();
        this.contentsDate = feed.getContentsDate();
        this.photoSpotName = feed.getPhotoSpot().getName();
        this.address = feed.getLocation().getAddress();
        this.lawRegion = feed.getPhotoSpot().getLawCode();
        this.latitude = feed.getLocation().getLatitude();
        this.longitude = feed.getLocation().getLongitude();
        this.createdAt = feed.getCreatedAt();
        this.coupleImage = feed.getCouple().getImage();
        this.coupleName = feed.getCouple().getName();
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
