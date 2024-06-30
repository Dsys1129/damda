package com.damda.global.config.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    COUPLE_CODE("coupleCode", 5 * 60, 1000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;
}
