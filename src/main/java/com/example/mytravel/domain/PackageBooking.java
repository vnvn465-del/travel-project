package com.example.mytravel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("PACKAGE") // 이 엔티티의 구분자 값
public class PackageBooking extends Booking {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private TravelPackage travelPackage;

    @Column(nullable = false)
    private LocalDate departureDate; // 실제 출발일

    @Column(nullable = false)
    private int travelerCount; // 총 여행 인원
}