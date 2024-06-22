package com.damda.user.controller;

import com.damda.global.auth.resolver.LoginUser;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.user.dto.UserLoginRequestDTO;
import com.damda.user.dto.UserSignupRequestDTO;
import com.damda.user.dto.UserUpdateRequestDTO;
import com.damda.user.entity.User;
import com.damda.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDTO> signup(@RequestPart("image") MultipartFile image,
                                                  @RequestPart("user") UserSignupRequestDTO requestDTO) {
        BaseResponseDTO response = userService.signup(image, requestDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO, HttpServletRequest httpServletRequest) {
        BaseResponseDTO response = userService.login(requestDTO, httpServletRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<BaseResponseDTO> updateUserInfo(@PathVariable Long id,
                                                          @RequestPart(name = "image") MultipartFile image,
                                                          @RequestPart(name = "user") UserUpdateRequestDTO requestDTO,
                                                          @LoginUser User user) {
        BaseResponseDTO response = userService.updateUserInfo(id, image, requestDTO, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<BaseResponseDTO> deleteUser(@PathVariable Long id, @LoginUser User user) {
        BaseResponseDTO response = userService.deleteUser(id, user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponseDTO> logout(HttpServletRequest request) {
        BaseResponseDTO response = userService.logout(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
