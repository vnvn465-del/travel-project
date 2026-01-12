package com.example.mytravel.repository;

import com.example.mytravel.domain.Booking;
import com.example.mytravel.domain.PackageBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // 마이페이지에서 특정 사용자의 모든 예약 내역을 조회
    List<Booking> findByUserId(Long userId);

    // 예약 번호로 특정 예약을 조회
    Optional<Booking> findByBookingNumber(String bookingNumber);


    //특정 사용자의 '패키지 여행 예약' 목록 전체를 조회하는 JPQL 쿼리입니다.
    //예약 날짜(bookingDate)를 기준으로 최신순으로 정렬하여 반환합니다.
    @Query("select pb from PackageBooking pb where pb.user.id = :userId order by pb.bookingDate desc")
    List<PackageBooking> findPackageBookingsByUserId(Long userId);


    //특정 사용자의 특정 '패키지 여행 예약' 한 건을 상세 조회하는 JPQL 쿼리입니다.
    //조회 시 예약 정보(PackageBooking)와 결제 정보(Payment)를 한 번의 쿼리로 함께 가져옵니다 (N+1 문제 방지).
    @Query("select pb from PackageBooking pb join fetch pb.payment where pb.id = :bookingId and pb.user.id = :userId")
    Optional<PackageBooking> findPackageBookingByIdAndUserId(Long bookingId, Long userId);
}