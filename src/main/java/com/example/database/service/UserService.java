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
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("로그인 시도: " + email);
        if(email == null || email.isEmpty()) {
            System.out.println(("email 비엇거나 null인데용ㅇ?????"));
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        System.out.println("사용자 정보: " + user.getEmail());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_USER") // 기본 권한 추가
                .build();
    }

    public User registerUser(User user) {
        System.out.println("회원가입 시도: " + user.getEmail() + ", " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화

        // 저장 전 로그 추가
        System.out.println("저장 전 비밀번호: " + user.getPassword());
        User savedUser = userRepository.save(user);

        // 저장 후 로그 추가
        System.out.println("회원가입 완료: " + savedUser.getEmail());
        return savedUser;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
