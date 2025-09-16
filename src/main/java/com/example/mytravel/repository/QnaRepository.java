package com.example.mytravel.repository;

import com.example.mytravel.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    // 특정 상품에 대한 모든 문의를 조회
    List<Qna> findByTravelPackageId(Long packageId);

    // 특정 사용자가 작성한 모든 문의를 조회
    List<Qna> findByUserId(Long userId);
}