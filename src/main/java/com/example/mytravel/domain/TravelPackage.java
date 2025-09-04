package com.example.mytravel.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity @Getter @NoArgsConstructor
public class TravelPackage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String country;
    @Column(nullable = false) private String city;
    @Column(length = 1000) private String description;
    @Column(nullable = false) private int price;
    private String image;
    private boolean isPopular;
}