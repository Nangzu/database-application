package com.example.database.repository;

import com.example.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p.category1 FROM Product p WHERE p.category1 IS NOT NULL")
    List<String> findDistinctCategory1();

    @Query("SELECT DISTINCT p.category2 FROM Product p WHERE p.category1 = :category1 AND p.category2 IS NOT NULL")
    List<String> findDistinctCategory2ByCategory1(@Param("category1") String category1);

    @Query("SELECT DISTINCT p.category3 FROM Product p WHERE p.category2 = :category2 AND p.category3 IS NOT NULL")
    List<String> findDistinctCategory3ByCategory2(@Param("category2") String category2);

    @Query("SELECT DISTINCT p.category4 FROM Product p WHERE p.category3 = :category3 AND p.category4 IS NOT NULL")
    List<String> findDistinctCategory4ByCategory3(@Param("category3") String category3);

    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR :searchQuery IS NULL) AND " +
            "(:category1 IS NULL OR p.category1 = :category1) AND " +
            "(:category2 IS NULL OR p.category2 = :category2) AND " +
            "(:category3 IS NULL OR p.category3 = :category3) AND " +
            "(:category4 IS NULL OR p.category4 = :category4)")
    List<Product> findBySearchAndCategories(
            @Param("searchQuery") String searchQuery,
            @Param("category1") String category1,
            @Param("category2") String category2,
            @Param("category3") String category3,
            @Param("category4") String category4
    );
}
