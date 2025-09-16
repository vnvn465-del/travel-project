package com.example.mytravel.repository;

import com.example.mytravel.domain.PackageBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageBookingRepository extends JpaRepository<PackageBooking, Long> {
}