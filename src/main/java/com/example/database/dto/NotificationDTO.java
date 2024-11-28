package com.example.database.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationDTO {
    private String message;

    public NotificationDTO(String message) {
        this.message = message;
    }

}
