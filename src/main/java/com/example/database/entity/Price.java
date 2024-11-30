package com.example.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_seq")
    @SequenceGenerator(name = "price_seq", sequenceName = "PRICE_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_num") // Product와의 관계
    @JsonBackReference // 순환 참조 방지
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shop_num") // Shop과의 관계
    @JsonBackReference // 순환 참조 방지
    private Shop shop;

    @Column(name = "price")
    private Integer price;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "price_update_date")
    private LocalDateTime priceUpdateDate;
}
