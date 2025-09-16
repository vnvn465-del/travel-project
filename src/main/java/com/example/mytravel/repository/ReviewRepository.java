package com.example.mytravel.repository;

import com.example.mytravel.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 상품에 달린 모든 리뷰를 조회
    List<Review> findByTravelPackageId(Long packageId);

    // 특정 사용자가 작성한 모든 리뷰를 조회
    List<Review> findByUserId(Long userId);
}