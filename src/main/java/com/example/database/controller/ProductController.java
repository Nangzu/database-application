package com.example.database.controller;

import com.example.database.service.NaverProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final NaverProductService naverProductService;

    @Autowired
    public ProductController(NaverProductService naverProductService) {
        this.naverProductService = naverProductService;
    }

    @GetMapping("/products/search")
    public ResponseEntity<String> searchProducts(@RequestParam String query) {
        try {
            // 네이버 API 호출
            String response = naverProductService.fetchProductDataFromNaver(query);
            return ResponseEntity.ok(response); // 성공 시 JSON 응답 반환
        } catch (Exception e) {
            // 에러 발생 시 500 상태와 메시지 반환
            return ResponseEntity.status(500).body("Error: Unable to fetch data from Naver API");
        }
    }
}
