package com.damda.main.service;

import com.damda.couple.repository.CoupleRepository;
import com.damda.feed.entity.PhotoSpot;
import com.damda.feed.repository.PhotoSpotRepository;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.main.dto.BestCoupleResponseDTO;
import com.damda.main.dto.BestPhotoSpotResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainService {

    private final CoupleRepository coupleRepository;
    private final PhotoSpotRepository photoSpotRepository;


    public BaseResponseDTO<List<BestCoupleResponseDTO>> getBestCouples() {
        List<BestCoupleResponseDTO> result = coupleRepository.findTop5ByTotalLikes();
        return BaseResponseDTO.getBaseResponse200WithData("베스트커플 조회 성공 ", result);
    }

    public BaseResponseDTO<List<BestPhotoSpotResponseDTO>> getBestPhotoSpots() {
        List<PhotoSpot> topPhotoSpots = photoSpotRepository.findTop4ByTotalLikes();
        List<Long> topPhotoSpotIds = topPhotoSpots.stream().map(PhotoSpot::getId).collect(Collectors.toList());
        List<BestPhotoSpotResponseDTO> result = photoSpotRepository.getBestPhotoSpotsThumbnails(topPhotoSpotIds);
        return BaseResponseDTO.getBaseResponse200WithData("베스트 스팟 조회 성공", result);
    }
}
