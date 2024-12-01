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

        // 페이징 설정
        int page = 0; // 0번째 페이지
        int size = 10; // 페이지 크기
        int startRow = page * size + 1; // 시작 행
        int endRow = startRow + size - 1; // 종료 행

        // 메서드 호출
        List<Product> products = productRepository.findBySearchAndCategories(
                searchQuery, category1, category2, category3, category4, startRow, endRow
        );

        // 결과 확인
        assertNotNull(products, "Products should not be null");

        // 출력 (테스트용)
        products.forEach(product -> {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Title: " + product.getTitle());
        });
    }
}
