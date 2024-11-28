package com.example.database.repository;

import com.example.database.entity.Wishlist;
import com.example.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;


public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    boolean existsByUserAndProductId(User user, Long productId);

    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.user = :user AND w.product.id = :productId")
    void deleteByUserAndProductId(@Param("user") User user, @Param("productId") Long productId);


    Optional<Wishlist> findByUserAndProductId(User user, Long productId);


}
