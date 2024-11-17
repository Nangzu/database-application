package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // 상품명
    private int price;         // 최저가
    private String link;       // 상품 상세 링크
    private String mallName;   // 판매 쇼핑몰 이름
    private String category1;  // 카테고리 1
    private String category2;  // 카테고리 2
    private String category3;  // 카테고리 3
    private String category4;  // 카테고리 4
}
