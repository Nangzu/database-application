package com.example.database.repository;

import com.example.database.entity.Alarm;
import com.example.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByUser(User user);
}
