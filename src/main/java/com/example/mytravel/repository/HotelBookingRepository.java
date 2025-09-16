package com.example.mytravel.repository;

import com.example.mytravel.domain.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
}