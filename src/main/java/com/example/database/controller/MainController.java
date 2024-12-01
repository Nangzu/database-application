package com.example.database.controller;

import com.example.database.entity.Product;
import com.example.database.entity.User;
import com.example.database.service.SearchHistoryService;
import com.example.database.service.SearchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SearchService searchService;

    // 메인 페이지
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        // 검색 조건 없이 전체 상품 조회
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser); // 템플릿에 로그인 사용자 정보 전달
        List<Product> products = searchService.searchByCategories(null, null, null, null, null);
        model.addAttribute("products", products);
        return "search_results"; // 검색 결과 화면
    }

    // 검색 로직
    @GetMapping("/main/search")
    public String search(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String category1,
            @RequestParam(required = false) String category2,
            @RequestParam(required = false) String category3,
            @RequestParam(required = false) String category4,
            Model model
    ) {
        // 검색 수행
        List<Product> products = searchService.searchByCategories(category1, category2, category3, category4, searchQuery);

        // 검색 결과가 없을 경우 메시지 추가
        if (products.isEmpty()) {
            model.addAttribute("message", "검색된 상품이 없습니다.");
        }
        model.addAttribute("products", products);
        return "search_results"; // 검색 결과 화면
    }
}
