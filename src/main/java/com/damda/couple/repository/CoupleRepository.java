package com.damda.couple.repository;

import com.damda.couple.dto.CoupleSearchResponseDTO;
import com.damda.couple.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {

    @Query("SELECT new com.damda.couple.dto.CoupleSearchResponseDTO(c.id, c.image, c.name) FROM Couple c where c.name LIKE %:keyword%")
    List<CoupleSearchResponseDTO> searchCouplesByKeyword(String keyword);
}
