package com.example.mytravel.dto;

import com.example.mytravel.domain.Itinerary;
import lombok.Getter;

@Getter
public class ItineraryDetailDto {
    private int dayNumber;
    private String title;
    private String description;
    private String mealInfo;
    private String hotelInfo;

    public ItineraryDetailDto(Itinerary itinerary) {
        this.dayNumber = itinerary.getDayNumber();
        this.title = itinerary.getTitle();
        this.description = itinerary.getDescription();
        this.mealInfo = itinerary.getMealInfo();
        this.hotelInfo = itinerary.getHotelInfo();
    }
}