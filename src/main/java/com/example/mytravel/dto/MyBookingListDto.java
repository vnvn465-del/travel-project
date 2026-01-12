package com.example.mytravel.dto;

import com.example.mytravel.domain.PackageBooking;
import com.example.mytravel.domain.enums.BookingStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class MyBookingListDto {

    private final Long bookingId;
    private final String bookingNumber;
    private final String productName;
    private final String thumbnailImageUrl;
    private final String departureDate;
    private final int totalPrice;
    private final BookingStatus status;

    // PackageBooking 엔티티를 이 DTO로 변환하는 생성자
    public MyBookingListDto(PackageBooking booking) {
        this.bookingId = booking.getId();
        this.bookingNumber = booking.getBookingNumber();
        this.productName = booking.getTravelPackage().getName();
        this.thumbnailImageUrl = booking.getTravelPackage().getImages().stream()
                .filter(img -> img.isThumbnail())
                .map(img -> img.getImageUrl())
                .findFirst().orElse(null); // 썸네일 이미지 URL
        this.departureDate = booking.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.totalPrice = booking.getTotalPrice();
        this.status = booking.getStatus();
    }
}