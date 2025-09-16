package com.example.mytravel.domain;

import com.example.mytravel.domain.enums.ItineraryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("FLIGHT")
public class FlightBooking extends Booking {
    private String itineraryApiId; // 항공 API로부터 받은 여정 고유 ID

    @Enumerated(EnumType.STRING)
    private ItineraryType itineraryType; // 여정 타입 (ONE_WAY, ROUND_TRIP)

    @OneToMany(mappedBy = "flightBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightPassenger> passengers;
}