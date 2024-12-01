package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pricemin")
public class Pricemin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_min_num")
    private Long priceMinNum;  // 가격 변경 기록의 고유 번호 (auto-increment)

    @Column(name = "pro_key")
    private Long proKey;  // 상품 ID

    @Column(name = "price_min")
    private Integer priceMin;  // 최저가

    @Column(name = "price_min_time")
    private LocalDateTime priceMinTime;  // 가격 변경 시간


}