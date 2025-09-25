package com.example.mytravel.dto;

import com.example.mytravel.domain.PackageBooking;
import lombok.Getter;

// API 응답으로 필요한 최소한의 정보만 담는 DTO
@Getter
public class BookingResponseDto {

    private final String bookingNumber;
    private final int totalPrice;
    private final PackageInfo packageInfo;
    private final UserInfo userInfo;

    // 엔티티를 DTO로 변환하는 생성자
    public BookingResponseDto(PackageBooking entity) {
        this.bookingNumber = entity.getBookingNumber();
        this.totalPrice = entity.getTotalPrice();
        this.packageInfo = new PackageInfo(entity.getTravelPackage().getName());
        this.userInfo = new UserInfo(entity.getUser().getEmail(), entity.getUser().getName());
    }

    // 중첩 DTO를 사용하여 필요한 정보만 노출
    @Getter
    private static class PackageInfo {
        private final String name;
        public PackageInfo(String name) { this.name = name; }
    }

    @Getter
    private static class UserInfo {
        private final String email;
        private final String name;
        public UserInfo(String email, String name) { this.email = email; this.name = name; }
    }
}