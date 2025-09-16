package com.example.mytravel.repository;

import com.example.mytravel.domain.PackageImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageImageRepository extends JpaRepository<PackageImage, Long> {
    // 특정 TravelPackage에 속한 모든 이미지를 조회하는 등의
    // 추가적인 쿼리 메소드가 필요하면 여기에 작성할 수 있습니다.
}