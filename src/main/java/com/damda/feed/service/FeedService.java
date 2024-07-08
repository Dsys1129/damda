package com.damda.feed.service;


import com.damda.couple.entity.Couple;
import com.damda.feed.dto.FeedDetailResponseDTO;
import com.damda.feed.dto.FeedListResponseDTO;
import com.damda.feed.dto.FeedRequestDTO;
import com.damda.feed.dto.PageParam;
import com.damda.feed.entity.Feed;
import com.damda.feed.entity.Like;
import com.damda.feed.entity.PhotoSpot;
import com.damda.feed.repository.FeedRepository;
import com.damda.feed.repository.LikeRepository;
import com.damda.feed.repository.PhotoSpotRepository;
import com.damda.global.config.LawRegionMapper;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.global.exception.ForbiddenException;
import com.damda.global.image.ImageFolderEnum;
import com.damda.global.image.ImageUploader;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final PhotoSpotRepository photoSpotRepository;
    private final LikeRepository likeRepository;
    private final ImageUploader imageUploader;

    @Transactional(readOnly = true)
    public BaseResponseDTO<FeedDetailResponseDTO> getFeedDetail(Long id, Couple couple) {
        Feed feed = feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 피드가 없습니다."));

        FeedDetailResponseDTO feedDetailResponseDTO = new FeedDetailResponseDTO(feed);

        if (couple != null) {
            Optional<Like> liked = likeRepository.findByFeedIdAndCoupleId(feed.getId(), couple.getId());
            if (liked.isPresent()){
                feedDetailResponseDTO.setLiked(true);
            }
        }

        return BaseResponseDTO.getBaseResponse200WithData("피드 상세 조회 완료", feedDetailResponseDTO);
    }

    @Transactional(readOnly = true)
//    @Cacheable(value = "popularFeeds", key = "#pageParam.page + '-' + #pageParam.size + '-' + #pageParam.filter")
    public Slice<FeedListResponseDTO> getPopularFeedList(PageParam pageParam, User user) {
        log.info("getPopularFeedList()");
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        Slice<FeedListResponseDTO> feedList = feedRepository.findPopularFeedList(pageParam.getFilter(), pageable);
        setLikedFeeds(user, feedList);
        return feedList;
    }

    @Transactional(readOnly = true)
    public Slice<FeedListResponseDTO> getRecentFeedList(PageParam pageParam, User user) {
        log.info("getRecentFeedList()");
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        Slice<FeedListResponseDTO> feedList = feedRepository.findRecentFeedList(pageParam.getFilter(), pageable);
        setLikedFeeds(user, feedList);
        return feedList;
    }

    private void setLikedFeeds(User user, Slice<FeedListResponseDTO> feedList) {
        if (user.getCouple() != null) {
            List<Long> feedIds = feedList.stream().map(FeedListResponseDTO::getId).collect(Collectors.toList());
            List<Long> likedFeedIds = likeRepository.findLikedFeedIdsByUserIdAndFeedIds(user.getId(), feedIds);
            feedList.forEach(feed -> {
                if (likedFeedIds.contains(feed.getId())) {
                    feed.setLiked(true);
                }
            });
        }
    }

    @Transactional
    public BaseResponseDTO createFeed(MultipartFile image, FeedRequestDTO requestDTO, Couple couple) {
        if (couple == null) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        String lawCode = LawRegionMapper.convertLawRegionToLawCode(requestDTO.getLawRegion());
        String uploadedImageFileName = imageUploader.upload(image, ImageFolderEnum.FEED);
        PhotoSpot photoSpot = photoSpotRepository.findByNameAndLawCode(requestDTO.getPhotoSpotName(), lawCode);
        if (photoSpot == null) {
            photoSpot = new PhotoSpot(requestDTO.getPhotoSpotName(), lawCode);
            photoSpotRepository.save(photoSpot);
        }
        Feed feed = Feed.createFeed(uploadedImageFileName, requestDTO, couple, photoSpot, LocalDateTime.now());
        Feed savedFeed = feedRepository.save(feed);
        return BaseResponseDTO.getBaseResponse201WithData("피드 생성 완료", Collections.singletonMap("feedId", savedFeed.getId()));
    }

    @Transactional
    public BaseResponseDTO updateFeed(Long id, MultipartFile image, FeedRequestDTO requestDTO, Couple couple) {
        Feed feed = feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 피드가 없습니다."));

        if (couple == null || !feed.getCouple().getId().equals(couple.getId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        String uploadedImageFileName = imageUploader.upload(image, ImageFolderEnum.FEED);
        String lawCode = LawRegionMapper.convertLawRegionToLawCode(requestDTO.getLawRegion());
        PhotoSpot photoSpot = photoSpotRepository.findByNameAndLawCode(requestDTO.getPhotoSpotName(), lawCode);

        if (photoSpot == null) {
            photoSpot = new PhotoSpot(requestDTO.getPhotoSpotName(), lawCode);
            photoSpotRepository.save(photoSpot);
        }
        feed.updateFeed(uploadedImageFileName, requestDTO, photoSpot, LocalDateTime.now());

        return BaseResponseDTO.getBaseResponse200WithData("피드 수정 완료", Collections.singletonMap("feedId", id));
    }

    @Transactional
    public BaseResponseDTO deleteFeed(Long id, Couple couple) {
        Feed feed = feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 피드가 없습니다."));

        if (couple == null || !feed.getCouple().getId().equals(couple.getId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        feedRepository.delete(feed);
        return BaseResponseDTO.getBaseResponse200WithoutData("피드 삭제 완료");
    }
}
