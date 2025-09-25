package com.example.mytravel.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("PACKAGE") // 이 엔티티의 구분자 값
@AllArgsConstructor
public class PackageBooking extends Booking {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private TravelPackage travelPackage;

    @Column(nullable = false)
    private LocalDate departureDate; // 실제 출발일

    @Column(nullable = false)
    private int travelerCount; // 총 여행 인원
}