package com.example.mytravel.repository;

import com.example.mytravel.domain.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    /**
     * isPopular 필드 값에 따라 상품 목록을 조회합니다.
     * @param isPopular true 또는 false
     * @return 조건에 맞는 상품 리스트
     */
    List<TravelPackage> findByIsPopular(boolean isPopular);
}