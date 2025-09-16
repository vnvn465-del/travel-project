package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class FlightPassenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_booking_id", nullable = false)
    private FlightBooking flightBooking;

    @Column(nullable = false)
    private String lastName; // 성 (영문)

    @Column(nullable = false)
    private String firstName; // 이름 (영문)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; // 성별

    @Column(nullable = false)
    private LocalDate birthDate; // 생년월일

    private String passportNumber; // 여권번호
}