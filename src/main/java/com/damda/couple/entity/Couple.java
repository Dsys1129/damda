package com.damda.couple.entity;

import com.damda.feed.entity.Feed;
import com.damda.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "couples")
@Entity
public class Couple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "d_day", nullable = false)
    private Integer dDay;

    @BatchSize(size = 2)
    @OneToMany(mappedBy = "couple" , fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "couple", fetch = FetchType.LAZY)
    private List<Feed> feeds = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Couple(String image, String name, Integer dDay, User user, LocalDateTime createdAt) {
        this.image = image;
        this.name = name;
        this.dDay = dDay;
        this.users.add(user);
        this.createdAt = createdAt;
    }

    public void updateInfo(String uploadedFileName, String name, Integer dDay, LocalDateTime now) {
        this.image = uploadedFileName;
        this.name = name;
        this.dDay = dDay;
        this.updatedAt = now;
    }
}
