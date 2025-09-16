package com.example.mytravel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private TravelPackage travelPackage;

    @Column(nullable = false)
    private int dayNumber; // N일차

    @Column(nullable = false)
    private String title; // 일정 제목 (예: "파리 시내 핵심 투어")

    @Column(columnDefinition = "TEXT")
    private String description; // 상세 설명

    private String mealInfo; // 식사 정보 (예: "조식: 호텔식, 중식: 현지식, 석식: 자유식")
    private String hotelInfo; // 숙소 정보 (예: "파리 시내 4성급 호텔")
}