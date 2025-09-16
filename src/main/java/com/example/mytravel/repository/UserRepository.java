package com.example.mytravel.repository;

import com.example.mytravel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Security에서 로그인 시 사용할 메소드
    // 이메일을 기준으로 사용자를 찾아옵니다.
    Optional<User> findByEmail(String email);
}