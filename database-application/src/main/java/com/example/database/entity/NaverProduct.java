package com.example.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "NAVER_PRODUCT")
public class NaverProduct {
    
    @Id
    private String productId; // 상품 ID (primary key)

    @Column
    private String title;

    @Column
    private String link;

    @Column
    private String image;

    @Column
    private int lprice;

    @Column
    private int hprice;

    @Column
    private String mallName;

    @Column
    private String brand;

    @Column
    private String category1;

    @Column
    private String category2;

    @Column
    private String category3;

    @Column
    private String category4;
    
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLprice(int lprice) {
        this.lprice = lprice;
    }

    public void setHprice(int hprice) {
        this.hprice = hprice;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }
}
