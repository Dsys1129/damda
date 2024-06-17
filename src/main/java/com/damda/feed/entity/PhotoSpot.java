package com.damda.feed.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    public PhotoSpot(String name, String lawCode) {
        this.name = name;
        this.lawCode = lawCode;
    }
}
