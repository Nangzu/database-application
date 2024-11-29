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
        return searchHistoryRepository.findTop5ByUserIdOrderByShNumDesc(userId);
    }

    public void saveSearchHistory(SearchHistory searchHistory) {
        // 중복 확인 로직 추가
        boolean exists = searchHistoryRepository.existsByUserIdAndHistory(
                searchHistory.getUserId(), searchHistory.getHistory()
        );
        if (!exists) {
            searchHistoryRepository.save(searchHistory);
        }
    }

}