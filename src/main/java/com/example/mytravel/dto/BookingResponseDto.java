package com.example.mytravel.dto;

import com.example.mytravel.domain.PackageBooking;
import com.example.mytravel.domain.TravelPackage;
import com.example.mytravel.domain.User;
import lombok.Getter;

// API 응답으로 필요한 최소한의 정보만 담는 DTO
@Getter
public class BookingResponseDto {

    private final String bookingNumber;
    private final int totalPrice;
    private final PackageInfo packageInfo; // 중첩 DTO
    private final UserInfo userInfo;       // 중첩 DTO

    // 엔티티를 DTO로 변환하는 생성자
    public BookingResponseDto(PackageBooking entity) {
        this.bookingNumber = entity.getBookingNumber();
        this.totalPrice = entity.getTotalPrice();
        // ★★★ 여기서 TravelPackage와 User 엔티티를 가져와서 중첩 DTO를 생성해야 합니다. ★★★
        this.packageInfo = new PackageInfo(entity.getTravelPackage());
        this.userInfo = new UserInfo(entity.getUser());
    }

    // --- JavaScript가 기대하는 구조와 똑같은 중첩 클래스를 만듭니다. ---
    @Getter
    private static class PackageInfo {
        private final String name;
        // 생성자에서 TravelPackage 엔티티를 직접 받도록 수정
        public PackageInfo(TravelPackage travelPackage) {
            this.name = travelPackage.getName();
        }
    }

    @Getter
    private static class UserInfo {
        private final String email;
        private final String name;
        // 생성자에서 User 엔티티를 직접 받도록 수정
        public UserInfo(User user) {
            this.email = user.getEmail();
            this.name = user.getName();
        }
    }
}