package com.example.database.controller;

import com.example.database.dto.NotificationDTO;
import com.example.database.entity.User;
import com.example.database.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {


    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationDTO> getNotifications(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // 로그인된 사용자에 대한 알림 반환
            return notificationService.getNotificationsForUser(loggedInUser);
        }

        return Collections.emptyList(); // 로그인되지 않은 경우 빈 리스트 반환
    }
}
