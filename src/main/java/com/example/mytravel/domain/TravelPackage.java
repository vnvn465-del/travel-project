package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.PackageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.mytravel.domain.Theme;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class TravelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 상품명 (예: "알프스의 보석, 스위스 일주 8일")

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description; // 상품 요약 설명

    @Column(nullable = false)
    private int price; // 1인 기준 시작 가격

    @Column(nullable = false)
    private String travelPeriod; // 여행 기간 (예: "6박 8일")

    private LocalDate departureDateStart; // 출발 가능 시작일
    private LocalDate departureDateEnd; // 출발 가능 종료일

    private int minTravelers; // 최소 출발 인원
    private int maxTravelers; // 최대 예약 가능 인원

    @Enumerated(EnumType.STRING)
    private PackageStatus status = PackageStatus.ON_SALE; // 상품 상태 (판매중, 판매종료)

    private boolean isPopular = false; // 인기 상품 여부

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Itinerary> itineraries = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "package_theme",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> themes = new HashSet<>();
}