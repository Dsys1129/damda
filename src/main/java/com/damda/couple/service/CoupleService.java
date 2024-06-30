package com.damda.couple.service;

import com.damda.couple.dto.CoupleCreateRequestDTO;
import com.damda.couple.dto.CoupleDetailResponseDTO;
import com.damda.couple.dto.CoupleUpdateRequestDTO;
import com.damda.couple.entity.Couple;
import com.damda.couple.repository.CoupleRepository;
import com.damda.global.config.cache.CacheType;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.global.exception.ForbiddenException;
import com.damda.global.image.ImageFolderEnum;
import com.damda.global.image.ImageUploader;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoupleService {

    private final CoupleRepository coupleRepository;
    private final ImageUploader imageUploader;
    private final CacheManager cacheManager;

    @Transactional
    public BaseResponseDTO<Map<String, String>> createCouple(MultipartFile image, CoupleCreateRequestDTO requestDTO, User user) {
        if (user.getCouple() != null) {
            throw new IllegalArgumentException("커플 그룹이 이미 존재 합니다.");
        }

        String uploadedFileName = imageUploader.upload(image, ImageFolderEnum.COUPLE);
        Couple couple = new Couple(uploadedFileName, requestDTO.getName(), requestDTO.getDDay(), user, LocalDateTime.now());

        String randomCode = UUID.randomUUID().toString().substring(0,6);
        Cache cache = cacheManager.getCache(CacheType.COUPLE_CODE.getCacheName());
        cache.put(randomCode, couple);

        return BaseResponseDTO.getBaseResponse201WithData("커플 생성 성공", Collections.singletonMap("code", randomCode));
    }

    @Transactional
    public BaseResponseDTO joinCouple(String code, User user) {
        Cache cache = cacheManager.getCache(CacheType.COUPLE_CODE.getCacheName());

        Couple couple = cache.get(code, Couple.class);

        if (couple == null) {
            throw new IllegalArgumentException("유효하지 않은 코드입니다.");
        }

        if (couple.getUsers().get(0).getId().equals(user.getId())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        cache.evict(code);
        couple.getUsers().add(user);
        coupleRepository.save(couple);
        return BaseResponseDTO.getBaseResponse200WithoutData("커플 매칭 성공");
    }

    public BaseResponseDTO<CoupleDetailResponseDTO> getCoupleDetails(Long id) {
        Couple findCouple = coupleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("커플 정보가 존재하지 않습니다."));

        CoupleDetailResponseDTO result = new CoupleDetailResponseDTO(findCouple);
        return BaseResponseDTO.getBaseResponse200WithData("커플 상세 조회 성공", result);
    }

    @Transactional
    public BaseResponseDTO updateCoupleInfo(MultipartFile image, CoupleUpdateRequestDTO requestDTO, Long id, User user) {
        Couple findCouple = coupleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("커플 정보가 존재하지 않습니다."));

        if (!findCouple.getId().equals(user.getCouple().getId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        String uploadedFileName = imageUploader.upload(image, ImageFolderEnum.COUPLE);
        findCouple.updateInfo(uploadedFileName, requestDTO.getName(), requestDTO.getDDay(), LocalDateTime.now());

        return BaseResponseDTO.getBaseResponse200WithoutData("커플 정보 수정 완료");
    }

    @Transactional
    public BaseResponseDTO deleteCouple(Long id, User user) {
        Couple findCouple = coupleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("커플 정보가 존재하지 않습니다."));

        if (!findCouple.getId().equals(user.getCouple().getId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }
        List<User> groupUsers = findCouple.getUsers();
        for (User groupUser : groupUsers) {
            groupUser.deleteCouple();
        }
        coupleRepository.delete(findCouple);
        return BaseResponseDTO.getBaseResponse200WithoutData("커플 삭제 성공");
    }
}
