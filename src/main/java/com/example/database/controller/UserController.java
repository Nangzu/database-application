package com.example.database.controller;

import com.example.database.service.UserService;
import com.example.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @GetMapping("/register")
    public String RegisterPage() {
        return "register";  // 로그인 페이지 이름
    }
}
