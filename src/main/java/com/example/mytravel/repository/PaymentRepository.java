package com.example.mytravel.repository;

import com.example.mytravel.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 특정 예약에 대한 결제 정보를 조회
    Optional<Payment> findByBookingId(Long bookingId);
}