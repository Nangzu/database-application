package com.example.database.controller;

import com.example.database.service.UserService;
import com.example.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.registerUser(user);
        return "redirect:/users/login";
    }

    @GetMapping("/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 로그인 페이지 이름
    }


    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            return "redirect:/main";  // 로그인 성공 후 메인 페이지로 리다이렉트
        } else {
            return "redirect:/users/login?error=true";  // 실패 시 로그인 페이지로 돌아가기
        }
    }

    @GetMapping("/register")
    public String RegisterPage() {
        return "register";  // 로그인 페이지 이름
    }


}
