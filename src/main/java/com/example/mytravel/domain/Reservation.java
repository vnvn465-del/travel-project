package com.example.mytravel.domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter @NoArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "travel_package_id", nullable = false) private TravelPackage travelPackage;
    @Column(nullable = false) private LocalDate reservationDate;
    @Column(nullable = false) private int numberOfTravelers;
    private Long totalPrice;
    @Column(nullable = false) private String status;
    @Column(updatable = false) private LocalDateTime createdAt;
    @PrePersist public void createdAt(){ this.createdAt = LocalDateTime.now(); }
    @Builder
    public Reservation(User user, TravelPackage travelPackage, LocalDate reservationDate, int numberOfTravelers, Long totalPrice, String status) {
        this.user = user;
        this.travelPackage = travelPackage;
        this.reservationDate = reservationDate;
        this.numberOfTravelers = numberOfTravelers;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}