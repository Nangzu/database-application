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
    List<SearchHistory> findTop5ByUserIdOrderByShNumDesc(Long userId);  // 최근 5개의 검색 기록을 가져옵니다.
}