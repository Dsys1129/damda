package com.damda.feed.dto;

import lombok.Getter;

@Getter
public class FeedListResponseDTO {
    private Long id;
    private String image;
    private String contents;
    private String coupleName;
    private String coupleImage;
    private boolean isLiked = false;

    public FeedListResponseDTO(Long id, String image, String contents, String coupleName, String coupleImage) {
        this.id = id;
        this.image = image;
        this.contents = contents;
        this.coupleName = coupleName;
        this.coupleImage = coupleImage;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Long getId() {
        return id;
    }
}
