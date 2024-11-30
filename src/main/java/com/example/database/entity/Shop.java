package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_seq")
    @SequenceGenerator(name = "shop_seq", sequenceName = "SHOP_SEQ", allocationSize = 1)
    @Column(name = "shop_num")
    private Long shopNum;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_url", length = 1000)
    private String shopUrl;

    @Column(name = "shop_trust")
    private Long shopTrust;

    @Column(name = "shop_api")
    private String shopApi;

    @Column(name = "name")
    private String name;
}
