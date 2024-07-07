package com.damda.feed.service;

import com.damda.couple.entity.Couple;
import com.damda.feed.entity.Feed;
import com.damda.feed.entity.Like;
import com.damda.feed.repository.FeedRepository;
import com.damda.feed.repository.LikeRepository;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.global.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public BaseResponseDTO doLike(Long id, Couple couple) {
        if (couple == null) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        Feed feed = feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));

        Optional<Like> liked = likeRepository.findByFeedIdAndCoupleId(feed.getId(), couple.getId());

        if (!liked.isPresent()) {
            Like like = new Like(feed,couple);
            likeRepository.save(like);
            feed.increaseLikeCount();
            return BaseResponseDTO.getBaseResponse200WithoutData("좋아요 등록 성공");
        }
        likeRepository.delete(liked.get());
        feed.decreaseLikesCount();
        return BaseResponseDTO.getBaseResponse200WithoutData("좋아요 해제 성공");
    }
}
