package com.example.mytravel.service;
import com.example.mytravel.domain.User;
import com.example.mytravel.dto.UserSignUpDto;
import com.example.mytravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public User signUp(UserSignUpDto signUpDto) {
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        User user = signUpDto.toEntity(passwordEncoder);
        return userRepository.save(user);
    }
}