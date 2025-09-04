package com.example.mytravel.repository;
import com.example.mytravel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}