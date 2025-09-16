package com.example.mytravel.repository;

import com.example.mytravel.domain.FlightPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightPassengerRepository extends JpaRepository<FlightPassenger, Long> {
}