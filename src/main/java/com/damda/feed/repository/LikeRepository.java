package com.damda.feed.repository;

import com.damda.feed.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByFeedIdAndCoupleId(Long feedId, Long coupleId);

    @Query("SELECT l.feed.id FROM Like l WHERE l.couple.id = :coupleId AND l.feed.id IN :feedIds")
    List<Long> findLikedFeedIdsByUserIdAndFeedIds(@Param("coupleId") Long coupleId, @Param("feedIds") List<Long> feedIds);
}
