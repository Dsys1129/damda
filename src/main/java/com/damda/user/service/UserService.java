package com.damda.user.service;


import com.damda.global.dto.BaseResponseDTO;
import com.damda.global.exception.ForbiddenException;
import com.damda.global.exception.InvalidCredentialsException;
import com.damda.global.exception.UserDuplicateException;
import com.damda.global.exception.UserNotFoundException;
import com.damda.global.image.ImageFolderEnum;
import com.damda.global.image.ImageUploader;
import com.damda.user.dto.UserLoginRequestDTO;
import com.damda.user.dto.UserSignupRequestDTO;
import com.damda.user.dto.UserUpdateRequestDTO;
import com.damda.user.entity.User;
import com.damda.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploader imageUploader;


    public BaseResponseDTO signup(MultipartFile image, UserSignupRequestDTO requestDTO) {
        Optional<User> findUser = userRepository.findByUserId(requestDTO.getUserId());

        if (findUser.isPresent()) {
            throw new UserDuplicateException("중복된 username 입니다.");
        }

        String uploadedImageName = imageUploader.upload(image, ImageFolderEnum.USER);
        String encryptedPassword = passwordEncoder.encrypt(requestDTO.getUserId(), requestDTO.getPassword());
        User newUser = User.createNewUser(uploadedImageName, encryptedPassword, requestDTO, LocalDateTime.now());
        userRepository.save(newUser);
        return BaseResponseDTO.getBaseResponse201WithoutData("회원가입 성공");
    }

    public BaseResponseDTO login(UserLoginRequestDTO requestDTO, HttpServletRequest httpRequest) {
        User findUser = userRepository.findByUserId(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저가 없습니다."));

        if (!passwordEncoder.isMatches(requestDTO.getPassword(), findUser)) {
            throw new InvalidCredentialsException("아이디 및 비밀번호가 일치하지 않습니다.");
        }

        httpRequest.getSession().setAttribute("user", findUser);
        return BaseResponseDTO.getBaseResponse200WithoutData("로그인 성공");
    }

    public BaseResponseDTO logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return BaseResponseDTO.getBaseResponse200WithoutData("로그아웃 성공");
    }

    public BaseResponseDTO updateUserInfo(Long id, MultipartFile image, UserUpdateRequestDTO requestDTO, User user) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

        if (!findUser.getId().equals(user.getId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        // update
        String uploadedImageName = imageUploader.upload(image, ImageFolderEnum.USER);
        findUser.updateUserInfo(uploadedImageName, requestDTO, LocalDateTime.now());
        return BaseResponseDTO.getBaseResponse200WithoutData("회원 수정 성공");
    }

    public BaseResponseDTO deleteUser(Long id, User user) {
        if (!user.getId().equals(id)) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        userRepository.deleteById(id);
        return BaseResponseDTO.getBaseResponse200WithoutData("회원 삭제 성공");
    }
}
