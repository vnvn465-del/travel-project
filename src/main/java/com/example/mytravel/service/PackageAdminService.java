package com.example.mytravel.service;

import com.example.mytravel.domain.*;
import com.example.mytravel.dto.PackageFormDto;
import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PackageAdminService {

    private final TravelPackageRepository travelPackageRepository;
    private final PackageImageRepository packageImageRepository;
    private final ItineraryRepository itineraryRepository;
    private final FileService fileService;

    // 상품 등록
    public Long savePackage(PackageFormDto dto) throws IOException {
        // 1. TravelPackage 엔티티 생성 및 저장
        TravelPackage travelPackage = createTravelPackage(dto);
        travelPackageRepository.save(travelPackage);

        // 2. PackageImage 엔티티 생성 및 저장
        savePackageImages(dto.getImageFiles(), travelPackage);

        // 3. Itinerary 엔티티 생성 및 저장
        saveItineraries(dto, travelPackage);

        return travelPackage.getId();
    }

    // 전체 상품 목록 조회 (관리자용)
    @Transactional(readOnly = true)
    public List<PackageListDto> findPackages() {
        return travelPackageRepository.findAll().stream()
                .map(PackageListDto::new)
                .collect(Collectors.toList());
    }

    // (이하 상품 수정/삭제 로직은 필요시 추가 구현)

    private TravelPackage createTravelPackage(PackageFormDto dto) {
        // DTO -> Entity 변환 로직
        TravelPackage travelPackage = new TravelPackage(); // 실제로는 빌더 패턴 등을 사용하는 것이 좋음
        // dto의 필드를 travelPackage 엔티티에 세팅...
        // ...
        return travelPackage;
    }

    private void savePackageImages(List<MultipartFile> imageFiles, TravelPackage travelPackage) throws IOException {
        for (int i = 0; i < imageFiles.size(); i++) {
            MultipartFile imageFile = imageFiles.get(i);
            if (!imageFile.isEmpty()) {
                String imageUrl = fileService.uploadFile(imageFile);
                PackageImage packageImage = new PackageImage();
                packageImage.setTravelPackage(travelPackage);
                packageImage.setImageUrl(imageUrl);
                // 첫 번째 이미지를 썸네일로 설정
                packageImage.setThumbnail(i == 0);
                packageImageRepository.save(packageImage);
            }
        }
    }

    private void saveItineraries(PackageFormDto dto, TravelPackage travelPackage) {
        dto.getItineraries().forEach(itineraryDto -> {
            Itinerary itinerary = new Itinerary();
            itinerary.setTravelPackage(travelPackage);
            // itineraryDto의 필드를 itinerary 엔티티에 세팅...
            // ...
            itineraryRepository.save(itinerary);
        });
    }
}