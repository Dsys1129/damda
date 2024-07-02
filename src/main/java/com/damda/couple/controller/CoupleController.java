package com.damda.couple.controller;

import com.damda.couple.dto.CoupleCreateRequestDTO;
import com.damda.couple.dto.CoupleDetailResponseDTO;
import com.damda.couple.dto.CoupleSearchResponseDTO;
import com.damda.couple.dto.CoupleUpdateRequestDTO;
import com.damda.couple.service.CoupleService;
import com.damda.global.auth.resolver.LoginUser;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CoupleController {

    private final CoupleService coupleService;

    @GetMapping("/couples/{id}")
    public ResponseEntity<BaseResponseDTO> getCoupleDetails(@PathVariable Long id) {
        BaseResponseDTO<CoupleDetailResponseDTO> response = coupleService.getCoupleDetails(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/couples")
    public ResponseEntity<BaseResponseDTO> createCouple(@RequestPart("image") MultipartFile image,
                                                        @RequestPart("couple") CoupleCreateRequestDTO requestDTO,
                                                        @LoginUser User user) {
        BaseResponseDTO<Map<String, String>> response = coupleService.createCouple(image, requestDTO, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/couples/search")
    public ResponseEntity<BaseResponseDTO<List<CoupleSearchResponseDTO>>> searchCouple(@RequestParam String keyword) {
        BaseResponseDTO<List<CoupleSearchResponseDTO>> response = coupleService.searchCouple(keyword);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/couples/{code}")
    public ResponseEntity<BaseResponseDTO> joinCouple(@PathVariable String code, @LoginUser User user) {
        BaseResponseDTO response = coupleService.joinCouple(code, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/couples/{id}")
    public ResponseEntity<BaseResponseDTO> updateCoupleInfo(@PathVariable Long id,
                                                            @RequestPart(name = "image") MultipartFile image,
                                                            @RequestPart(name = "couple") CoupleUpdateRequestDTO requestDTO,
                                                            @LoginUser User user) {
        BaseResponseDTO response = coupleService.updateCoupleInfo(image, requestDTO, id, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/couples/{id}")
    public ResponseEntity<BaseResponseDTO> deleteCouple(@PathVariable Long id,
                                                        @LoginUser User user) {
        BaseResponseDTO response = coupleService.deleteCouple(id, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
