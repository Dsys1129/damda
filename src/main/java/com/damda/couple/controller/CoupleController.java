package com.damda.couple.controller;

import com.damda.couple.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CoupleController {

    private final CoupleService coupleService;

}
