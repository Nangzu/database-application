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

    private final SearchHistoryService searchHistoryService;

    @Autowired
    private SearchService searchService;

    // 메인 페이지
    @GetMapping("/main")
    public String main(Model model, HttpSession session,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // 로그인 사용자 정보 추가
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        // 페이징된 전체 상품 조회
        long totalItems = searchService.countSearchResults(null, null, null, null, null); // 전체 상품 개수
        int totalPages = (int) Math.ceil((double) totalItems / size);

        List<Product> products = searchService.getAllProducts(page, size);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // 페이징된 검색 결과 조회
        long totalItems = searchService.countSearchResults(searchQuery, category1, category2, category3, category4);
        System.out.println("Total items: " + totalItems);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        System.out.println("Total pages: " + totalPages);

        List<Product> products = searchService.searchProducts(searchQuery, category1, category2, category3, category4, page, size);

        // 검색 결과가 없을 경우 메시지 추가
        if (products.isEmpty()) {
            model.addAttribute("message", "검색된 상품이 없습니다.");
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "search_results"; // 검색 결과 화면
    }
}
