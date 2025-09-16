package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private String paymentMethod; // 결제 수단 (CARD, BANK_TRANSFER 등)

    @Column(nullable = false)
    private int amount; // 결제 금액

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제 완료 시점

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // 결제 상태 (SUCCESS, FAILED, CANCELLED)

    @Column(unique = true, nullable = false)
    private String pgTransactionId; // PG사 거래 고유 번호
}