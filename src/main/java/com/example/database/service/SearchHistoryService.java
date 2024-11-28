package com.example.database.service;

import com.example.database.entity.SearchHistory;
import com.example.database.entity.User;
import com.example.database.repository.SearchHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SearchHistoryService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> getRecentSearchHistory(Long userId) {
        return searchHistoryRepository.findTop5ByUserIdOrderByShNumDesc(userId);  // 사용자 ID를 기준으로 최근 5개 검색 기록을 가져옵니다.
    }
    public void saveSearchHistory(SearchHistory searchHistory) {
        searchHistoryRepository.save(searchHistory);  // DB에 저장
    }
}