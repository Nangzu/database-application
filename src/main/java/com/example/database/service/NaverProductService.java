package com.example.database.service;

import com.example.database.dto.NaverProductDto;
import com.example.database.dto.NaverRequestVariableDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NaverProductService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/shop.json";

    public List<NaverProductDto> naverShopSearchAPI(NaverRequestVariableDto requestDto) {
        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();

        // 요청 URL 생성
        String url = UriComponentsBuilder.fromHttpUrl(NAVER_API_URL)
                .queryParam("query", requestDto.getQuery())
                .queryParam("display", requestDto.getDisplay())
                .queryParam("start", requestDto.getStart())
                .queryParam("sort", requestDto.getSort())
                .toUriString();

        // HTTP 헤더 추가
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

        // API 호출 및 응답 처리
        org.springframework.http.ResponseEntity<Map> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                Map.class
        );

        // 응답 데이터 추출 및 매핑
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        return items.stream().map(this::convertToNaverProductDto).collect(Collectors.toList());
    }

    // 응답 데이터를 NaverProductDto로 변환
    private NaverProductDto convertToNaverProductDto(Map<String, Object> item) {
        NaverProductDto dto = new NaverProductDto();
        dto.setTitle((String) item.get("title"));
        dto.setLink((String) item.get("link"));
        dto.setImage((String) item.get("image"));
        dto.setLprice(Integer.parseInt((String) item.get("lprice")));
        dto.setHprice(item.containsKey("hprice") ? Integer.parseInt((String) item.get("hprice")) : 0);
        dto.setMallName((String) item.get("mallName"));
        dto.setProductId((String) item.get("productId"));
        dto.setBrand((String) item.get("brand"));
        dto.setCategory1((String) item.get("category1"));
        dto.setCategory2((String) item.get("category2"));
        dto.setCategory3((String) item.get("category3"));
        dto.setCategory4((String) item.get("category4"));
        return dto;
    }
}
