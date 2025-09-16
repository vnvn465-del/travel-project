package com.example.mytravel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("HOTEL")
public class HotelBooking extends Booking {
    private String hotelApiId; // 호텔 API로부터 받은 호텔 고유 ID
    private String roomApiId;  // 호텔 API로부터 받은 객실 고유 ID

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private int numberOfGuests;
}