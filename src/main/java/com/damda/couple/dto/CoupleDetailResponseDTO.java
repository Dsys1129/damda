package com.damda.couple.dto;

import com.damda.couple.entity.Couple;
import com.damda.feed.entity.Feed;
import com.damda.feed.service.FeedListResponseDTO;
import com.damda.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CoupleDetailResponseDTO {

    private Long id;
    private String coupleImage;
    private String coupleName;
    private Integer dDay;
    private Integer feedCount;
    private List<UserResponseDTO> user;
    private List<FeedResponseDTO> feed;

    public CoupleDetailResponseDTO(Couple couple) {
        this.id = couple.getId();
        this.coupleImage = couple.getImage();
        this.coupleName = couple.getName();
        this.dDay = couple.getDDay();
        this.feedCount = couple.getFeeds().size();
        this.user = couple.getUsers().stream().map(UserResponseDTO::new).collect(Collectors.toList());
        this.feed = couple.getFeeds().stream().map(FeedResponseDTO::new).collect(Collectors.toList());
    }

    @Getter
    static class UserResponseDTO {
        private Long id;
        private Integer age;
        private String gender;

        public UserResponseDTO(User user) {
            this.id = user.getId();
            this.age = user.getAge();
            this.gender = user.getGender().name();
        }
    }

    @Getter
    static class FeedResponseDTO {
        private String image;

        public FeedResponseDTO(Feed feed) {
            this.image = feed.getImage();
        }
    }
}
