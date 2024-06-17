package com.damda.couple.service;

import com.damda.couple.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoupleService {

    private final CoupleRepository coupleRepository;

}
