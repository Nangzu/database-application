package com.example.database.controller;

import com.example.database.entity.Product;
import com.example.database.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category1")
    public ResponseEntity<List<String>> getCategory1() {
        return ResponseEntity.ok(productService.getCategory1());
    }

    @GetMapping("/category2")
    public ResponseEntity<List<String>> getCategory2(@RequestParam String category1) {
        return ResponseEntity.ok(productService.getCategory2(category1));
    }

    @GetMapping("/category3")
    public ResponseEntity<List<String>> getCategory3(@RequestParam String category2) {
        return ResponseEntity.ok(productService.getCategory3(category2));
    }

    @GetMapping("/category4")
    public ResponseEntity<List<String>> getCategory4(@RequestParam String category3) {
        return ResponseEntity.ok(productService.getCategory4(category3));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String category1,
            @RequestParam(required = false) String category2,
            @RequestParam(required = false) String category3,
            @RequestParam(required = false) String category4) {
        if (searchQuery == null && category1 == null && category2 == null && category3 == null && category4 == null) {
            // 검색 조건이 없을 경우 모든 상품 반환
            return ResponseEntity.ok(productService.getAllProducts());
        }
        return ResponseEntity.ok(productService.searchProducts(searchQuery, category1, category2, category3, category4));
    }
}
