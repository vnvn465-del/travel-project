package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // 상속 관계 전략
@DiscriminatorColumn(name = "booking_type") // 예약을 구분할 컬럼
@SuperBuilder
public abstract class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String bookingNumber; // 예약 번호 (P20251026-...)

    @Column(nullable = false)
    private LocalDateTime bookingDate; // 예약 시점

    @Column(nullable = false)
    private int totalPrice; // 최종 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // 예약 상태

    // Booking.java
    public void confirmBooking() {
        this.status = BookingStatus.CONFIRMED;
    }
}