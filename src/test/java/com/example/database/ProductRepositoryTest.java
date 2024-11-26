package com.example.database.repository;

import com.example.database.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByCategoriesAndSearch() {
        // 테스트용 데이터
        String category1 = "디지털/가전";
        String category2 = "영상가전";
        String category3 = "TV";
        String category4 = "LEDTV";
        String searchQuery = "LG전자";

        // 메서드 호출
        List<Product> products = productRepository.findBySearchAndCategories(
                category1, category2, category3, category4, searchQuery
        );

        // 결과 확인
        assertNotNull(products, "Products should not be null");
    }
}
