package com.example.mytravel.dto;

import com.example.mytravel.domain.Itinerary;
import com.example.mytravel.domain.PackageImage;
import com.example.mytravel.domain.TravelPackage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PackageDetailDto {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String description;
    private int price;
    private String travelPeriod;
    private LocalDate departureDateStart;
    private LocalDate departureDateEnd;
    private int minTravelers;

    // 상세 페이지 갤러리에 표시될 모든 이미지의 URL 리스트
    private List<String> imageUrls;

    // 상세 페이지에 표시될 모든 상세 일정 정보 리스트
    private List<ItineraryDetailDto> itineraries;

    // 엔티티를 DTO로 변환하는 정적 팩토리 메소드 (생성자보다 가독성이 좋음)
    public static PackageDetailDto fromEntity(TravelPackage travelPackage) {
        return new PackageDetailDto(travelPackage);
    }

    private PackageDetailDto(TravelPackage travelPackage) {
        this.id = travelPackage.getId();
        this.name = travelPackage.getName();
        this.country = travelPackage.getCountry();
        this.city = travelPackage.getCity();
        this.description = travelPackage.getDescription();
        this.price = travelPackage.getPrice();
        this.travelPeriod = travelPackage.getTravelPeriod();
        this.departureDateStart = travelPackage.getDepartureDateStart();
        this.departureDateEnd = travelPackage.getDepartureDateEnd();
        this.minTravelers = travelPackage.getMinTravelers();

        // 엔티티의 이미지 리스트를 -> 이미지 URL(String) 리스트로 변환
        this.imageUrls = travelPackage.getImages().stream()
                .map(PackageImage::getImageUrl)
                .collect(Collectors.toList());

        // 엔티티의 상세 일정 리스트를 -> ItineraryDetailDto 리스트로 변환
        this.itineraries = travelPackage.getItineraries().stream()
                .map(ItineraryDetailDto::new) // ItineraryDetailDto의 생성자 참조
                .collect(Collectors.toList());
    }
}