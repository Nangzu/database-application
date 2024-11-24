package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String link;
    private String image;
    private Integer lprice;
    private Integer hprice;
    private String mallName;
    private String productId;
    private Integer productType;
    private String brand;
    private String maker;
    private String category1;
    private String category2;
    private String category3;
    private String category4;
}
