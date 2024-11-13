package com.example.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false,unique = true,length = 30)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
