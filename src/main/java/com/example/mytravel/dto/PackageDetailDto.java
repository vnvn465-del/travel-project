package com.example.mytravel.dto;

import com.example.mytravel.domain.TravelPackage;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class PackageDetailDto {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String description; // 상세 설명 또는 일정
    private int price;
    private String mainImage;
    private List<String> detailImages; // 상세 이미지 리스트

    @Builder
    public PackageDetailDto(Long id, String name, String country, String city, String description, int price, String mainImage, List<String> detailImages) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.description = description;
        this.price = price;
        this.mainImage = mainImage;
        this.detailImages = detailImages;
    }

    // Entity를 DTO로 변환하는 정적 팩토리 메소드
    public static PackageDetailDto fromEntity(TravelPackage entity) {
        // 임시로 상세 이미지를 하드코딩합니다. DB에 필드를 추가하는 것이 가장 좋습니다.
        List<String> images = Arrays.asList(entity.getImage(), "review-greece.jpg", "review-singapore.jpg", "dest-danang.jpg");

        // 엔티티의 description 필드에 있는 줄바꿈 문자를 <br> 태그로 변환합니다.
        String formattedDescription = entity.getDescription().replaceAll("\\r\\n|\\n|\\r", "<br>");

        return PackageDetailDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                // ... (다른 필드들)
                .description(formattedDescription) // 변환된 내용을 DTO에 담습니다.
                .price(entity.getPrice())
                .mainImage(entity.getImage())
                .detailImages(images)
                .build();
    }
}