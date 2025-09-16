package com.example.mytravel.repository;

import com.example.mytravel.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // 마이페이지에서 특정 사용자의 모든 예약 내역을 조회
    List<Booking> findByUserId(Long userId);

    // 예약 번호로 특정 예약을 조회
    Optional<Booking> findByBookingNumber(String bookingNumber);
}