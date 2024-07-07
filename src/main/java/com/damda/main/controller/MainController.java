package com.damda.main.controller;

import com.damda.global.auth.resolver.LoginUser;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.main.dto.BestCoupleResponseDTO;
import com.damda.main.dto.BestPhotoSpotResponseDTO;
import com.damda.main.service.MainService;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final MainService mainService;

    @GetMapping("/best-spots")
    public ResponseEntity<BaseResponseDTO<List<BestPhotoSpotResponseDTO>>> getBestSpots(@LoginUser User user) {
        BaseResponseDTO<List<BestPhotoSpotResponseDTO>> response = mainService.getBestPhotoSpots();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/best-couples")
    public ResponseEntity<BaseResponseDTO<List<BestCoupleResponseDTO>>> getBestCouples(@LoginUser User user) {
        BaseResponseDTO<List<BestCoupleResponseDTO>> response = mainService.getBestCouples();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
