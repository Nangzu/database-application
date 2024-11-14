package com.example.database.service;


import com.example.database.dto.NaverProductDto;
import com.example.database.dto.NaverRequestVariableDto;
import com.example.database.entity.NaverProduct;
import com.example.database.repository.NaverProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaverProductService {
	
	private final NaverProductRepository naverProductRepository;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/shop.json";
    
    public NaverProductService(NaverProductRepository naverProductRepository) {
        this.naverProductRepository = naverProductRepository;
    }

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
        
        // API 응답 로그 출력 (확인용)
        log.info("API 응답: {}", response.getBody());

        // 응답 데이터 추출 및 매핑
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
        
        // DTO로 변환 후 저장
        List<NaverProduct> naverProducts = items.stream()
                .map(this::convertToNaverProduct)
                .collect(Collectors.toList());

        // DB에 저장
        try {
            naverProductRepository.saveAll(naverProducts);  // DB에 저장
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            e.printStackTrace();
        }
        
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
    
    private NaverProduct convertToNaverProduct(Map<String, Object> item) {
        NaverProduct productEntity = new NaverProduct();
        productEntity.setProductId((String) item.get("productId"));
        productEntity.setTitle((String) item.get("title"));
        productEntity.setLink((String) item.get("link"));
        productEntity.setImage((String) item.get("image"));
        productEntity.setLprice(Integer.parseInt((String) item.get("lprice")));
        productEntity.setHprice(item.containsKey("hprice") ? Integer.parseInt((String) item.get("hprice")) : 0);
        productEntity.setMallName((String) item.get("mallName"));
        productEntity.setBrand((String) item.get("brand"));
        productEntity.setCategory1((String) item.get("category1"));
        productEntity.setCategory2((String) item.get("category2"));
        productEntity.setCategory3((String) item.get("category3"));
        productEntity.setCategory4((String) item.get("category4"));
        return productEntity;
    }
}
