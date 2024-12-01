package com.example.database.service;

import com.example.database.entity.Product;
import com.example.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepository productRepository;

    public List<String> getCategory1() {
        return productRepository.findDistinctCategory1();
    }

    public List<String> getCategory2(String category1) {
        return productRepository.findDistinctCategory2ByCategory1(category1);
    }

    public List<String> getCategory3(String category2) {
        return productRepository.findDistinctCategory3ByCategory2(category2);
    }

    public List<String> getCategory4(String category3) {
        return productRepository.findDistinctCategory4ByCategory3(category3);
    }

    public List<Product> searchProducts(
            String searchQuery,
            String category1,
            String category2,
            String category3,
            String category4,
            int page,
            int size) {

        // `startRow`와 `endRow` 계산
        int startRow = page * size ; // 0부터 시작
        int endRow = startRow + size ;

        // 데이터베이스에서 페이징된 결과를 가져옴
        return productRepository.findBySearchAndCategories(
                searchQuery, category1, category2, category3, category4, startRow, endRow);


    }

    public List<Product> getAllProducts(int page, int size) {
        int startRow = page * size;
        int endRow = startRow + size;
        return productRepository.findBySearchAndCategories(null, null, null, null, null, startRow, endRow);
    }

    // 전체 페이지 개수 계산
    // 검색 결과의 총 개수를 계산
    public long countSearchResults(String searchQuery, String category1, String category2, String category3, String category4) {
        return productRepository.countBySearchAndCategories(searchQuery, category1, category2, category3, category4);
    }

    // 전체 페이지 수 계산
    public int calculateTotalPages(String searchQuery, String category1, String category2, String category3, String category4, int size) {
        long totalProducts = countSearchResults(searchQuery, category1, category2, category3, category4); // 검색에 맞는 총 상품 개수
        return (int) Math.ceil((double) totalProducts / size); // 전체 페이지 수 계산
    }


}

