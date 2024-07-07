package com.damda.feed.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "photo_spots")
@Entity
public class PhotoSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // 법정동 코드
    @Column(name = "lawCode", nullable = false, length = 10)
    private String lawCode;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "photoSpot", fetch = FetchType.LAZY)
    private List<Feed> feeds = new ArrayList<>();

    public PhotoSpot(String name, String lawCode) {
        this.name = name;
        this.lawCode = lawCode;
    }
}
