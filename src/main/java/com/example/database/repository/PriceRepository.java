package com.example.database.repository;

import com.example.database.entity.Price;
import com.example.database.entity.Product;
import com.example.database.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {


    // 특정 가격 정보 조회 (Product와 Shop 정보를 포함)
    List<Price> findByProductAndShop(Product product, Shop shop);

    // Price ID로 Product 및 Shop 정보와 함께 조회
    @Query("SELECT p FROM Price p JOIN FETCH p.product JOIN FETCH p.shop WHERE p.id = :priceId")
    Optional<Price> findPriceWithProductAndShop(@Param("priceId") Long priceId);
}