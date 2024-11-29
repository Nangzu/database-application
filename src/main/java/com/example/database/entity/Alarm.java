package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALARM")
@Data
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alarm_seq")
    @SequenceGenerator(name = "alarm_seq", sequenceName = "ALARM_SEQ", allocationSize = 1)
    @Column(name = "ALARM_NUM")
    private Long alarmNum;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "ALARM_CONFIG", nullable = false)
    private Integer alarmConfig; // 1(ON), 0(OFF)
}
