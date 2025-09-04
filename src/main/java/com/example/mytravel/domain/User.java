package com.example.mytravel.domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Table(name = "users")
@Getter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    // ============== [추가된 컬럼] ==============
    @Column(nullable = false)
    private LocalDate birthdate; // 생년월일 (날짜 형식을 위해 LocalDate 타입 사용)

    @Column(nullable = false)
    private String phoneNumber; // 휴대폰 번호

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String addressDetail; // 상세주소
    // ==========================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String name, LocalDate birthdate, String phoneNumber, String address, String addressDetail, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        // ============== [추가된 필드 생성자] ==============
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        // =============================================
        this.role = role;
    }
}