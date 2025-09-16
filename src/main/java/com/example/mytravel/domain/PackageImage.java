package com.example.mytravel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PackageImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private TravelPackage travelPackage;

    @Column(nullable = false)
    private String imageUrl; // 이미지 파일 경로 또는 URL

    @Column(nullable = false)
    private boolean isThumbnail; // 대표 이미지(썸네일) 여부
}