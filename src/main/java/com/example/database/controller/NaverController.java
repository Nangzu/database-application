package com.example.database.controller;

import com.example.database.dto.NaverRequestVariableDto;
import com.example.database.dto.NaverProductDto;
import com.example.database.service.NaverProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NaverController {

    private final NaverProductService naverProductService;

    public NaverController(NaverProductService naverProductService) {
        this.naverProductService = naverProductService;
    }

    @GetMapping("/naver-search")
    public String searchNaverProducts(Model model) {
        // DTO 생성
        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
                .query("example") // 검색어
                .display(9)       // 표시할 개수
                .start(1)         // 시작 위치
                .sort("sim")      // 정렬 기준
                .build();

        // 서비스 호출
        List<NaverProductDto> naverProductDtos = naverProductService.naverShopSearchAPI(naverRequestVariableDto);

        // View에 데이터 전달
        model.addAttribute("naverProductList", naverProductDtos);

        return "naverProductView"; // 렌더링할 뷰 이름
    }
}
