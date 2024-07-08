package com.damda.feed.controller;

import com.damda.feed.dto.FeedDetailResponseDTO;
import com.damda.feed.dto.FeedListResponseDTO;
import com.damda.feed.dto.FeedRequestDTO;
import com.damda.feed.dto.PageDTO;
import com.damda.feed.service.FeedService;
import com.damda.global.auth.resolver.LoginUser;
import com.damda.global.dto.BaseResponseDTO;
import com.damda.feed.dto.PageParam;
import com.damda.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds/{id}")
    public ResponseEntity<BaseResponseDTO<FeedDetailResponseDTO>> getFeedDetail(@PathVariable Long id, @LoginUser User user) {
        BaseResponseDTO<FeedDetailResponseDTO> response = feedService.getFeedDetail(id, user.getCouple());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/feeds")
    public ResponseEntity<PageDTO<List<FeedListResponseDTO>>> getFeedList(@ModelAttribute PageParam pageParam,
                                                                          @LoginUser User user) {

        log.info("FeedList : {} {} {} {}", pageParam.getFilter(), pageParam.getSort());
        Slice<FeedListResponseDTO> result;
        if ("new".equals(pageParam.getSort())) {
            result = feedService.getRecentFeedList(pageParam, user);
        } else {
            result = feedService.getPopularFeedList(pageParam, user);
        }
        PageDTO<List<FeedListResponseDTO>> response = new PageDTO<>(result.getContent(), result.hasNext());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/feeds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDTO> createFeed(@RequestPart(name = "image") MultipartFile image,
                                                      @RequestPart(name = "feed") FeedRequestDTO requestDTO,
                                                      @LoginUser User user) {
        log.info("create Feed {} {}", requestDTO.getLawRegion(), requestDTO.getPhotoSpotName());
        BaseResponseDTO response = feedService.createFeed(image, requestDTO, user.getCouple());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/feeds/{id}")
    public ResponseEntity<BaseResponseDTO> updateFeed(@PathVariable Long id,
                                                      @RequestPart(name = "image") MultipartFile image,
                                                      @RequestPart(name = "feed") FeedRequestDTO requestDTO,
                                                      @LoginUser User user) {
        BaseResponseDTO response = feedService.updateFeed(id, image, requestDTO, user.getCouple());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/feeds/{id}")
    public ResponseEntity<BaseResponseDTO> deleteFeed(@PathVariable Long id, @LoginUser User user) {
        BaseResponseDTO response = feedService.deleteFeed(id, user.getCouple());
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
