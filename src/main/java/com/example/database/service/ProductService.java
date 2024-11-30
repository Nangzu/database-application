package com.example.database.service;

import com.example.database.entity.Price;
import com.example.database.entity.Product;
import com.example.database.entity.Shop;
import com.example.database.repository.PriceRepository;
import com.example.database.repository.ProductRepository;
import com.example.database.repository.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final PriceRepository priceRepository;

    public ProductService(ProductRepository productRepository, ShopRepository shopRepository, PriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.priceRepository = priceRepository;
    }
    @Transactional
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public Optional<Product> getProductByIdWithShop(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        productOpt.ifPresent(product -> {
            // 가격 정보와 연관된 Shop 정보를 초기화
            product.getPrices().forEach(price -> price.getShop());
        });
        return productOpt;
    }

    public void savePrice(Price price) {
        // 가격을 저장
        priceRepository.save(price);
    }


}