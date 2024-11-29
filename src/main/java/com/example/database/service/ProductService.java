package com.example.database.service;

import com.example.database.entity.Product;
import com.example.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

//    @Autowired
//    private ProductRepository productRepository;

//    public List<String> getCategory1() {
//        return productRepository.findDistinctCategory1();
//    }
//
//    public List<String> getCategory2(String category1) {
//        return productRepository.findDistinctCategory2ByCategory1(category1);
//    }
//
//    public List<String> getCategory3(String category2) {
//        return productRepository.findDistinctCategory3ByCategory2(category2);
//    }
//
//    public List<String> getCategory4(String category3) {
//        return productRepository.findDistinctCategory4ByCategory3(category3);
//    }
//
//    public List<Product> searchProducts(String searchQuery, String category1, String category2, String category3, String category4) {
//        return productRepository.findBySearchAndCategories(searchQuery, category1, category2, category3, category4);
//    }
//
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
}
