package com.example.mytravel.dto;

import com.example.mytravel.domain.PackageImage;
import com.example.mytravel.domain.TravelPackage;
import lombok.Getter;

@Getter
public class PackageListDto {

    private Long id;
    private String name;
    private String country;
    private String city;
    private int price;
    private String thumbnailImageUrl; // 상품의 대표(썸네일) 이미지 URL

    // 엔티티를 DTO로 변환하는 생성자
    public PackageListDto(TravelPackage travelPackage) {
        this.id = travelPackage.getId();
        this.name = travelPackage.getName();
        this.country = travelPackage.getCountry();
        this.city = travelPackage.getCity();
        this.price = travelPackage.getPrice();

        // 상품 이미지들 중에서 썸네일(isThumbnail=true)인 이미지의 URL을 찾아서 설정
        this.thumbnailImageUrl = travelPackage.getImages().stream()
                .filter(PackageImage::isThumbnail) // isThumbnail()이 true인 것만 필터링
                .map(PackageImage::getImageUrl)     // 이미지 URL을 가져옴
                .findFirst()                        // 그 중 첫 번째 것을 찾음
                .orElse(null);                      // 썸네일이 없으면 null
    }
}