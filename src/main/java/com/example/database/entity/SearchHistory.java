package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SEARCHHISTORY")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_h_seq")
    @SequenceGenerator(name = "s_h_seq", sequenceName = "S_H_SEQ", allocationSize = 1)
    @Column(name = "s_h_num")
    private Long shNum;  // S_H_NUM

    @Column(name = "user_id")
    private Long userId; // USER_ID

    @Column(name = "history")
    private String history; // HISTORY


}
