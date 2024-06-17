package com.damda.feed.controller;

import com.damda.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;

}
