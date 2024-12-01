package com.example.database.service;

import com.example.database.entity.Product;
import com.example.database.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearchByCategories() {
        String category1 = "디지털/가전"; // 테스트 데이터
        String category2 = "영상가전";
        String category3 = "TV";
        String category4 = "LEDTV";
        String searchQuery = "LG전자";

        // 테스트용 페이징 설정
        int page = 0;
        int size = 10;

        // 메서드 호출
        List<Product> products = searchService.searchProducts(searchQuery, category1, category2, category3, category4, page, size);

        // 결과 확인
        assertNotNull(products, "Products should not be null");
        assert products.size() <= size : "Products should not exceed page size";
    }
}
