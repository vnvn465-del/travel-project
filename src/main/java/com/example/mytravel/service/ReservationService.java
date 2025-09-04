package com.example.mytravel.service;
import com.example.mytravel.domain.Reservation;
import com.example.mytravel.domain.TravelPackage;
import com.example.mytravel.domain.User;
import com.example.mytravel.dto.ReservationRequestDto;
import com.example.mytravel.repository.ReservationRepository;
import com.example.mytravel.repository.TravelPackageRepository;
import com.example.mytravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TravelPackageRepository travelPackageRepository;
    @Transactional
    public Reservation createReservation(ReservationRequestDto requestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        TravelPackage travelPackage = travelPackageRepository.findById(requestDto.getPackageId()).orElseThrow(() -> new IllegalArgumentException("여행 상품을 찾을 수 없습니다."));
        long totalPrice = (long) travelPackage.getPrice() * requestDto.getNumberOfTravelers();
        Reservation reservation = Reservation.builder()
                .user(user).travelPackage(travelPackage).reservationDate(requestDto.getReservationDate())
                .numberOfTravelers(requestDto.getNumberOfTravelers()).totalPrice(totalPrice).status("PENDING").build();
        return reservationRepository.save(reservation);
    }
}