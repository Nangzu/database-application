package com.example.database.dto;

import com.example.database.entity.Product;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class NaverProductDto {
    private String title;       // 상품명
    private String link;        // 상품 상세 링크
    private String image;       // 상품 이미지
    private int lprice;         // 최저가
    private int hprice;         // 최고가
    private String mallName;    // 판매 쇼핑몰 이름
    private String productId;   // 상품 ID
    private String brand;       // 브랜드
    private String category1;   // 카테고리 1
    private String category2;   // 카테고리 2
    private String category3;   // 카테고리 3
    private String category4;   // 카테고리 4

    // DTO -> Entity 변환 메서드
    public List<Product> toEntityList() {
        Product product = new Product();
        product.setName(this.title);
        product.setLink(this.link);
        product.setPrice(this.lprice);
        product.setMallName(this.mallName);
        product.setCategory1(this.category1);
        product.setCategory2(this.category2);
        product.setCategory3(this.category3);
        product.setCategory4(this.category4);
        return Collections.singletonList(product); // 단일 상품을 리스트로 반환
    }
}
