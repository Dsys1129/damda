package com.damda.user.entity;

import com.damda.couple.entity.Couple;
import com.damda.user.dto.UserSignupRequestDTO;
import com.damda.user.dto.UserUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "user_id", nullable = false, unique = true, length = 20)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 10)
    private String nickname;

    @Column(name = "age", nullable = false, length = 3)
    private Integer age;

    @Column(name = "gender", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Couple couple;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    private User(String profileImage, String userId, String password, String nickname, Integer age, Gender gender, LocalDateTime createdAt) {
        this.profileImage = profileImage;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.createdAt = createdAt;
    }

    public static User createNewUser(String imagePath, String encryptedPassword, UserSignupRequestDTO requestDTO, LocalDateTime createdAt) {
        return User.builder()
                .userId(requestDTO.getUserId())
                .password(encryptedPassword)
                .nickname(requestDTO.getNickname())
                .profileImage(imagePath)
                .age(requestDTO.getAge())
                .gender(Gender.valueOf(requestDTO.getGender()))
                .createdAt(createdAt)
                .build();
    }
    public void updateUserInfo(String profileImage, UserUpdateRequestDTO requestDTO, LocalDateTime now) {
        this.profileImage = profileImage;
        this.nickname = requestDTO.getNickname();
        this.age = requestDTO.getAge();
        this.updatedAt = now;
    }
}
