package com.example.mytravel.repository;

import com.example.mytravel.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    // 특정 사용자의 찜 목록 전체를 조회
    List<Wishlist> findByUserId(Long userId);
}