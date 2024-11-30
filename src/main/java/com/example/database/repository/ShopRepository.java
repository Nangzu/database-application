package com.example.database.repository;

import com.example.database.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByShopName(String shopName); // shopName으로 Shop 찾기
}
