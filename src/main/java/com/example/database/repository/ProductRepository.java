package com.example.database.repository;

import com.example.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 이름에 키워드가 포함된 상품 조회
    List<Product> findByNameContaining(String keyword);

    // 특정 가격 범위 내의 상품 조회
    List<Product> findByPriceBetween(int minPrice, int maxPrice);

    // 특정 카테고리의 상품 조회
    List<Product> findByCategory1(String category1);
}
