package com.example.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "link", length = 1000)
    private String link;

    @Column(name = "image", length = 1000)
    private String image;

    @Column(name = "lprice")
    private Integer lprice;

    @Column(name = "hprice")
    private Integer hprice;

    private String mallName;

    @Column(name ="product_id")
    private String productId;

    @Column(name = "product_type")
    private Integer productType;

    private String brand;
    private String maker;

    // 추가: 카테고리 필드
    @Column(name = "category1")
    private String category1;

    @Column(name = "category2")
    private String category2;

    @Column(name = "category3")
    private String category3;

    @Column(name = "category4")
    private String category4;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "cate_num") // Category와 연결
    private Category category;

    // Price 테이블과 연결 (Shop과의 간접적인 관계)
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonManagedReference // 순환 참조 방지
    private List<Price> prices;
}
