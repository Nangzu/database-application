package com.example.database.repository;


import com.example.database.entity.SearchHistory;
import com.example.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    // JPQL을 사용하여 중복 체크
    @Query("SELECT COUNT(sh) > 0 FROM SearchHistory sh WHERE sh.userId = :userId AND sh.history = :history")
    boolean existsByUserIdAndHistory(Long userId, String history);

    @Query("SELECT sh FROM SearchHistory sh WHERE sh.userId = :userId ORDER BY sh.shNum DESC")
    List<SearchHistory> findTop5ByUserIdOrderByShNumDesc(@Param("userId") Long userId);
}