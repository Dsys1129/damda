package com.damda.feed.entity;

import com.damda.couple.entity.Couple;
import com.damda.feed.dto.FeedRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feeds")
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "contents", nullable = false, length = 200)
    private String contents;

    @Column(name = "contents_date", nullable = false)
    private LocalDate contentsDate;

    @Embedded
    private Location location;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoSpot_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PhotoSpot photoSpot;

    private int likes;

    @Builder
    private Feed(String image, String contents, LocalDate contentsDate, LocalDateTime createdAt, Location location, Couple couple, PhotoSpot photoSpot) {
        this.image = image;
        this.contents = contents;
        this.contentsDate = contentsDate;
        this.createdAt = createdAt;
        this.location = location;
        this.couple = couple;
        this.photoSpot = photoSpot;
    }

    public static Feed createFeed(String image, FeedRequestDTO requestDTO, Couple couple, PhotoSpot photoSpot, LocalDateTime createdAt) {
        return Feed.builder()
                .image(image)
                .contents(requestDTO.getContents())
                .contentsDate(LocalDate.parse(requestDTO.getContentsDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .location(new Location(requestDTO.getLatitude(), requestDTO.getLongitude(), requestDTO.getAddress()))
                .createdAt(createdAt)
                .couple(couple)
                .photoSpot(photoSpot)
                .build();
    }

    public void updateFeed(String image, FeedRequestDTO requestDTO, PhotoSpot photoSpot, LocalDateTime updatedAt) {
        this.image = image;
        this.contents = requestDTO.getContents();
        this.contentsDate = LocalDate.parse(requestDTO.getContentsDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.location = new Location(requestDTO.getLatitude(), requestDTO.getLongitude(), requestDTO.getAddress());
        this.photoSpot = photoSpot;
        this.updatedAt = updatedAt;
    }

    public void increaseLikeCount() {
        this.likes++;
    }

    public void decreaseLikesCount() {
        if (likes < 1) {
            throw new IllegalArgumentException("좋아요 수는 음수가 될 수 없습니다.");
        }
        this.likes--;
    }
}
