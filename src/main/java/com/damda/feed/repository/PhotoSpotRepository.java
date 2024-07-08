package com.damda.feed.repository;


import com.damda.feed.entity.PhotoSpot;
import com.damda.main.dto.BestPhotoSpotResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoSpotRepository extends JpaRepository<PhotoSpot, Long> {

    PhotoSpot findByNameAndLawCode(String name, String lawCode);

    @Query("SELECT ps FROM PhotoSpot ps JOIN ps.feeds f GROUP BY ps.id ORDER BY SUM(f.likes) DESC")
    List<PhotoSpot> findTop4ByTotalLikes();

    @Query("SELECT new com.damda.main.BestPhotoSpotResponseDTO(ps.id, ps.name, ps.lawCode, f.image) " +
            "FROM PhotoSpot ps " +
            "JOIN Feed f ON ps.id = f.photoSpot.id " +
            "WHERE ps.id IN :photoSpotIds " +
            "AND f.likes = (SELECT MAX(f2.likes) FROM Feed f2 WHERE f2.photoSpot.id = ps.id) " +
            "ORDER BY ps.id")
    List<BestPhotoSpotResponseDTO> getBestPhotoSpotsThumbnails(@Param("photoSpotIds") List<Long> photoSpotIds);
}