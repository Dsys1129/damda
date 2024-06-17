package com.damda.feed.service;


import com.damda.feed.repository.FeedRepository;
import com.damda.feed.repository.PhotoSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final PhotoSpotRepository photoSpotRepository;

}
