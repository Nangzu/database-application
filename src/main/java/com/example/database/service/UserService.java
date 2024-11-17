package com.example.database.service;

import com.example.database.entity.User;
import com.example.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 인증 처리 시 비밀번호 비교
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }
        // 비밀번호 매칭 확인
        return password.equals(user.getPassword());
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
    // 사용자 정보 업데이트
    public User updateUser(User user) {
        return userRepository.save(user);  // 비밀번호 변경 후 사용자 정보 저장
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
