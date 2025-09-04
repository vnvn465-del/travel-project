package com.example.mytravel.service;
import com.example.mytravel.domain.TravelPackage;
import com.example.mytravel.dto.PackageDetailDto;
import com.example.mytravel.dto.PackageResponseDto;
import com.example.mytravel.repository.TravelPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class PackageService {

    private final TravelPackageRepository packageRepository;

    private final TravelPackageRepository travelPackageRepository;

    @Transactional(readOnly = true)
    public List<PackageResponseDto> getAllPackages() {
        return travelPackageRepository.findAll().stream()
                .map(PackageResponseDto::new) // .map(entity -> new PackageResponseDto(entity))와 동일
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PackageResponseDto> findPopularPackages() {
        return packageRepository.findByIsPopular(true).stream().map(PackageResponseDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<PackageResponseDto> findNormalPackages() {
        return packageRepository.findByIsPopular(false).stream().map(PackageResponseDto::new).collect(Collectors.toList());
    }



    /**
     * 여행 상품 ID를 기반으로 상세 정보를 조회합니다.
     * @param packageId 조회할 상품의 ID
     * @return 상품 상세 정보가 담긴 DTO
     */
    @Transactional(readOnly = true) // 데이터를 변경하지 않는 조회 트랜잭션은 readOnly=true로 설정하여 성능 최적화
    public PackageDetailDto getPackageDetail(Long packageId) {
        // 1. Repository를 통해 DB에서 ID에 해당하는 TravelPackage 엔티티를 찾습니다.
        TravelPackage travelPackage = travelPackageRepository.findById(packageId)
                // 2. 만약 해당 ID의 상품이 없다면, 예외를 발생시켜 사용자에게 알립니다.
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 여행 상품을 찾을 수 없습니다. id=" + packageId));

        // 3. 찾아온 엔티티를 DTO의 정적 팩토리 메소드를 사용하여 DTO로 변환합니다.
        return PackageDetailDto.fromEntity(travelPackage);
    }
}