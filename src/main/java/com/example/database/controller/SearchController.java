package com.example.database.controller;

import com.example.database.entity.Product;
import com.example.database.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class SearchController {
    @Autowired
    private  SearchService searchService;

//    @Autowired
//    public SearchController(SearchService searchService) {
//        this.searchService = searchService;
//    }
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
     * 카테고리 및 검색어를 기반으로 제품 검색
     *
     * @param category1 1단계 카테고리
     * @param category2 2단계 카테고리
     * @param category3 3단계 카테고리
     * @param category4 4단계 카테고리
     * @param searchQuery 검색어
     * @return 검색된 제품 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String category1,
            @RequestParam(required = false) String category2,
            @RequestParam(required = false) String category3,
            @RequestParam(required = false) String category4,
            @RequestParam(required = false) String searchQuery) {
        if (searchQuery == null && category1 == null && category2 == null && category3 == null && category4 == null) {
            // 검색 조건이 없을 경우 모든 상품 반환
            return ResponseEntity.ok(searchService.getAllProducts());
        }
//        List<Product> products = searchService.searchByCategories(category1, category2, category3, category4, searchQuery);
        return ResponseEntity.ok(searchService.searchProducts(searchQuery, category1, category2, category3, category4));
    }


}
