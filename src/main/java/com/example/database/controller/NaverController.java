//package com.example.database.controller;
//
//import com.example.database.service.NaverProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/naver")
//public class NaverController {
//
//    private final NaverProductService naverProductService;
//
//    @Autowired
//    public NaverController(NaverProductService naverProductService) {
//        this.naverProductService = naverProductService;
//    }
//
//    @GetMapping("/search")
//    public String searchNaverProducts(@RequestParam String query) {
//        return naverProductService.fetchProductDataFromNaver(query);
//    }
//}
