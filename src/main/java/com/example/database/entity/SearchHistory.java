package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SEARCHHISTORY")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shNum;  // S_H_NUM
    private Long userId; // USER_ID
    private String history; // HISTORY


}
