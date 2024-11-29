package com.example.database.controller;


import com.example.database.entity.SearchHistory;
import com.example.database.entity.User;
import com.example.database.service.SearchHistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchHistoryController {
    @Autowired
    private SearchHistoryService searchHistoryService;

    // 검색 기록을 JSON 형태로 반환하는 엔드포인트 추가
    @GetMapping("/api/search-history")
    @ResponseBody
    public List<SearchHistory> getSearchHistory(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return List.of();  // 비로그인 상태에서는 빈 리스트 반환
        }
        Long userId = loggedInUser.getId();
        return searchHistoryService.getRecentSearchHistory(userId); // 최근 검색 기록을 반환
    }


    @PostMapping("/api/search-history")
    @ResponseBody
    public ResponseEntity<String> addSearchHistory(@RequestBody SearchHistory searchHistory, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        searchHistory.setUserId(loggedInUser.getId());
        searchHistoryService.saveSearchHistory(searchHistory);
        return ResponseEntity.ok("검색 기록이 저장되었습니다.");
    }

}
