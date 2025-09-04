package com.example.mytravel.dto;
import com.example.mytravel.domain.TravelPackage;
import lombok.Getter;
@Getter
public class PackageResponseDto {
    private final Long id;
    private final String name;
    private final String country;
    private final String city;
    private final int price;
    private final String image;
    public PackageResponseDto(TravelPackage entity) {
        this.id = entity.getId(); this.name = entity.getName(); this.country = entity.getCountry(); this.city = entity.getCity(); this.price = entity.getPrice(); this.image = entity.getImage();
    }
}