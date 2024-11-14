package com.example.database.service;

import com.example.database.entity.User;
import com.example.database.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // 직접 이메일과 비밀번호를 확인하는 로그인 메서드
    public boolean authenticateUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("이메일 또는 비밀번호가 비어 있습니다.");
            return false;
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            System.out.println("사용자를 찾을 수 없습니다: " + email);
            return false;
        }

        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }

        System.out.println("로그인 성공: " + user.getEmail());
        return true;
    }

    // 사용자 등록 메서드
    public User registerUser(User user) {
        System.out.println("회원가입 시도: " + user.getEmail() + ", " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        User savedUser = userRepository.save(user);
        System.out.println("회원가입 완료: " + savedUser.getEmail());
        return savedUser;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
