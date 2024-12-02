package com.example.database.controller;

import com.example.database.entity.Product;
import com.example.database.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/category1")
    public ResponseEntity<List<String>> getCategory1() {
        return ResponseEntity.ok(searchService.getCategory1());
    }

    @GetMapping("/category2")
    public ResponseEntity<List<String>> getCategory2(@RequestParam String category1) {
        return ResponseEntity.ok(searchService.getCategory2(category1));
    }

    @GetMapping("/category3")
    public ResponseEntity<List<String>> getCategory3(@RequestParam String category2) {
        return ResponseEntity.ok(searchService.getCategory3(category2));
    }

    @GetMapping("/category4")
    public ResponseEntity<List<String>> getCategory4(@RequestParam String category3) {
        return ResponseEntity.ok(searchService.getCategory4(category3));
    }

    /**
     * 카테고리 및 검색어를 기반으로 페이징된 제품 검색
     *
     * @param category1   1단계 카테고리
     * @param category2   2단계 카테고리
     * @param category3   3단계 카테고리
     * @param category4   4단계 카테고리
     * @param searchQuery 검색어
     * @param page        페이지 번호
     * @param size        페이지 크기
     * @return 페이징된 검색 결과 및 관련 정보
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(
            @RequestParam(required = false) String category1,
            @RequestParam(required = false) String category2,
            @RequestParam(required = false) String category3,
            @RequestParam(required = false) String category4,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // 총 검색된 상품 수 계산
        long totalItems = searchService.countSearchResults(searchQuery, category1, category2, category3, category4);

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 검색된 상품 리스트 조회
        List<Product> productList = searchService.searchProducts(
                searchQuery, category1, category2, category3, category4, page, size);

        // 결과를 Map에 담아 반환
        Map<String, Object> response = new HashMap<>();
        response.put("products", productList);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);

        return ResponseEntity.ok(response);
    }
}