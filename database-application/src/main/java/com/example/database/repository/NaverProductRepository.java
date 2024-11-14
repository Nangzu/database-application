package com.example.database.repository;

import com.example.database.entity.NaverProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverProductRepository extends JpaRepository<NaverProduct, String> {
    // 상품 ID를 기준으로 상품을 찾는 메서드
    NaverProduct findByProductId(String productId);
}