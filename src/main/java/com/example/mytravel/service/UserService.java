package com.example.mytravel.service;
import com.example.mytravel.domain.User;
import com.example.mytravel.domain.enums.Role;
import com.example.mytravel.dto.UserSignUpDto;
import com.example.mytravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

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

    /**
     * 애플리케이션 시작 시, 관리자 계정이 없으면 기본 관리자 계정을 생성합니다.
     * @param adminEmail 관리자 이메일
     * @param adminPassword 관리자 비밀번호
     */
    public void createAdminUserIfNotExists(String adminEmail, String adminPassword) {
        // 1. 해당 이메일을 가진 사용자가 이미 DB에 있는지 확인합니다.
        Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);

        // 2. 사용자가 존재하지 않을 경우에만 새로 생성합니다.
        if (existingAdmin.isEmpty()) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword)) // 비밀번호는 반드시 암호화
                    .name("관리자")
                    .birthdate(LocalDate.now()) // 임시 생년월일
                    .phoneNumber("010-0000-0000") // 임시 연락처
                    .address("관리자 주소")
                    .addressDetail("상세주소")
                    .role(Role.ADMIN) // 역할을 ADMIN으로 설정
                    .build();

            userRepository.save(admin);
            System.out.println(">>> Default admin user created: " + adminEmail);
        }
    }
}