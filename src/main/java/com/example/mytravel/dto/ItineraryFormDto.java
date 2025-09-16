package com.example.mytravel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItineraryFormDto {

    private Long id; // 일정 수정 시 필요한 ID

    @NotNull(message = "일차 정보는 필수입니다.")
    @Min(value = 1, message = "1일차 이상이어야 합니다.")
    private Integer dayNumber;

    @NotBlank(message = "일정 제목은 필수입니다.")
    private String title;

    private String description;
    private String mealInfo;
    private String hotelInfo;
}