package com.damda.feed.repository;


import com.damda.feed.entity.PhotoSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoSpotRepository extends JpaRepository<PhotoSpot, Long> {

    PhotoSpot findByNameAndLawCode(String name, String lawCode);

}