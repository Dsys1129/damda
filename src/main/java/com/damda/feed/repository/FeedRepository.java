package com.damda.feed.repository;


import com.damda.feed.dto.FeedListResponseDTO;
import com.damda.feed.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {


    @Query("SELECT new com.damda.feed.dto.FeedListResponseDTO(f.id, f.image, f.contents, f.couple.name, f.couple.image) FROM Feed f WHERE f.photoSpot.lawCode LIKE :filter% ORDER BY f.likes DESC")
    Slice<FeedListResponseDTO> findPopularFeedList(String filter, Pageable pageable);

    @Query("SELECT new com.damda.feed.dto.FeedListResponseDTO(f.id, f.image, f.contents, f.couple.name, f.couple.image) FROM Feed f WHERE f.photoSpot.lawCode LIKE :filter% ORDER BY f.createdAt DESC")
    Slice<FeedListResponseDTO> findRecentFeedList(@Param("filter") String filter, Pageable pageable);
}
