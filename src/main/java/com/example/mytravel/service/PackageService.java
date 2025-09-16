package com.example.mytravel.service;

import com.example.mytravel.domain.TravelPackage;
import com.example.mytravel.dto.PackageDetailDto;
import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.repository.TravelPackageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // CUD 기능이 없으므로 클래스 레벨에 readOnly 설정
@RequiredArgsConstructor
public class PackageService {

    private final TravelPackageRepository travelPackageRepository;

    // 메인 페이지 - 인기 상품 조회
    public List<PackageListDto> findPopularPackages() {
        return travelPackageRepository.findByIsPopular(true).stream()
                .map(PackageListDto::new)
                .collect(Collectors.toList());
    }

    // 메인 페이지 - 일반 상품 조회
    public List<PackageListDto> findNormalPackages() {
        return travelPackageRepository.findByIsPopular(false).stream()
                .map(PackageListDto::new)
                .collect(Collectors.toList());
    }

    // 전체 상품 목록 페이지 - 모든 상품 조회
    public List<PackageListDto> findAllPackages() {
        return travelPackageRepository.findAll().stream()
                .map(PackageListDto::new)
                .collect(Collectors.toList());
    }

    // 상품 상세 페이지 - 특정 상품 상세 조회
    public PackageDetailDto findPackageDetail(Long packageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다. ID: " + packageId));

        // DTO의 정적 팩토리 메소드를 사용하여 변환
        return PackageDetailDto.fromEntity(travelPackage);
    }
}