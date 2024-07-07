package com.damda.feed.controller;

import com.damda.feed.service.LikeService;
import com.damda.global.auth.resolver.LoginUser;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feeds/likes/{id}")
    public ResponseEntity<BaseResponseDTO> like(@PathVariable Long id,
                                                @LoginUser User user) {
        BaseResponseDTO response = likeService.doLike(id, user.getCouple());
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
