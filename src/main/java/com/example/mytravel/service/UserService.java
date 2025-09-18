package com.example.mytravel.service;

import com.example.mytravel.domain.User;
import com.example.mytravel.domain.enums.Role;
import com.example.mytravel.dto.UserSignUpDto;
import com.example.mytravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService { // UserDetailsService 구현

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
    public User saveUser(UserSignUpDto signUpDto) {
        // 이메일 중복 검사
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        // 비밀번호 일치 여부 검사
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword())) // 비밀번호 암호화
                .birthdate(signUpDto.getBirthdate())
                .phoneNumber(signUpDto.getPhoneNumber())
                .address(signUpDto.getAddress())
                .addressDetail(signUpDto.getAddressDetail())
                .role(Role.USER) // 기본 권한은 USER
                .build();

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
                    .birthdate(LocalDate.now()) // 임시 데이터
                    .phoneNumber("010-0000-0000") // 임시 데이터
                    .address("관리자 주소")
                    .addressDetail("상세주소")
                    .role(Role.ADMIN) // 역할을 ADMIN으로 설정
                    .build();

            userRepository.save(admin);
            System.out.println(">>> Default admin user created: " + adminEmail);
        }
    }


    // Spring Security 로그인 인증 로직
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(() -> "ROLE_" + user.getRole().name())
        );
    }


}