package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email; // 로그인 ID

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false, length = 50)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private LocalDate birthdate; // 생년월일

    @Column(nullable = false, length = 20)
    private String phoneNumber; // 휴대폰 번호

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String addressDetail; // 상세주소

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 권한 (USER, ADMIN)




}