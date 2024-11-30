package com.example.database.service;

import com.example.database.entity.Shop;
import com.example.database.repository.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop findShopByShopName(String shopName) {
        return shopRepository.findByShopName(shopName); // Shop 엔티티의 shopName과 일치하는 Shop을 찾음
    }
}
