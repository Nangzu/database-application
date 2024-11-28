package com.example.database.controller;


import com.example.database.entity.SearchHistory;
import com.example.database.entity.User;
import com.example.database.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class SearchHistoryController {
    @Autowired
    private SearchHistoryService searchHistoryService;

    @GetMapping("/search-history")
    public String getSearchHistory(Model model, User user) {
        Long userId = user.getId();  // 현재 로그인한 사용자의 ID를 가져옵니다.
        List<SearchHistory> searchHistoryList = searchHistoryService.getRecentSearchHistory(userId);
        model.addAttribute("searchHistoryList", searchHistoryList);  // 검색 기록을 모델에 추가
        return "search-history";  // 검색 기록을 표시할 뷰로 이동
    }

    // 검색어를 받는 API
    @PostMapping("/api/search-history")
    public String addSearchHistory(@RequestBody SearchHistory searchHistory) {
        searchHistoryService.saveSearchHistory(searchHistory);  // 검색 기록을 저장하는 서비스 호출
        return "검색 기록이 저장되었습니다.";
    }
}
