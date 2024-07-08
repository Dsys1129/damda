package com.damda.global.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class LawRegionMapper {

    private static final Map<String, String> lawCodeRegionMapper = new ConcurrentHashMap<>();
    private static final Map<String, String> regionLawCodeMapper = new ConcurrentHashMap<>();

    @PostConstruct
    public void initLawCodeRegionMapper() {
        lawCodeRegionMapper.put("서울특별시 종로구", "1111000000");
        lawCodeRegionMapper.put("서울특별시 중구", "1114000000");
        lawCodeRegionMapper.put("서울특별시 용산구", "1117000000");
        lawCodeRegionMapper.put("서울특별시 성동구", "1120000000");
        lawCodeRegionMapper.put("서울특별시 광진구", "1121500000");
        lawCodeRegionMapper.put("서울특별시 동대문구", "1123000000");
        lawCodeRegionMapper.put("서울특별시 중랑구", "1126000000");
        lawCodeRegionMapper.put("서울특별시 성북구", "1129000000");
        lawCodeRegionMapper.put("서울특별시 강북구", "1130500000");
        lawCodeRegionMapper.put("서울특별시 도봉구", "1132000000");
        lawCodeRegionMapper.put("서울특별시 노원구", "1135000000");
        lawCodeRegionMapper.put("서울특별시 은평구", "1138000000");
        lawCodeRegionMapper.put("서울특별시 서대문구", "1141000000");
        lawCodeRegionMapper.put("서울특별시 마포구", "1144000000");
        lawCodeRegionMapper.put("서울특별시 양천구", "1147000000");
        lawCodeRegionMapper.put("서울특별시 강서구", "1150000000");
        lawCodeRegionMapper.put("서울특별시 구로구", "1153000000");
        lawCodeRegionMapper.put("서울특별시 금천구", "1154500000");
        lawCodeRegionMapper.put("서울특별시 영등포구", "1156000000");
        lawCodeRegionMapper.put("서울특별시 동작구", "1159000000");
        lawCodeRegionMapper.put("서울특별시 관악구", "1162000000");
        lawCodeRegionMapper.put("서울특별시 서초구", "1165000000");
        lawCodeRegionMapper.put("서울특별시 강남구", "1168000000");
        lawCodeRegionMapper.put("서울특별시 송파구", "1171000000");
        lawCodeRegionMapper.put("서울특별시 강동구", "1174000000");
    }

    @PostConstruct
    public void initRegionLawCodeMapper() {
        for (Map.Entry<String, String> entry : lawCodeRegionMapper.entrySet()) {
            regionLawCodeMapper.put(entry.getValue(), entry.getKey());
        }
    }

    public static String convertLawRegionToLawCode(String lawRegion) {
        String[] split = lawRegion.split(" ");
        return lawCodeRegionMapper.get(split[0] + " " + split[1]);
    }

    public static String convertLawCodeToLawRegion(String lawCode) {
        return regionLawCodeMapper.get(lawCode);
    }
}
