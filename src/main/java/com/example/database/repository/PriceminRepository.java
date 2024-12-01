package com.example.database.repository;

import com.example.database.entity.Pricemin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceminRepository extends JpaRepository<Pricemin, Long> {
    List<Pricemin> findByProKeyOrderByPriceMinTimeDesc(Long proKey);
}
