package com.example.mytravel.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingFormDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long packageId;

    @NotNull(message = "출발일은 필수입니다.")
    @Future(message = "출발일은 오늘 이후여야 합니다.")
    private LocalDate departureDate;

    @NotNull(message = "여행 인원은 필수입니다.")
    @Min(value = 1, message = "여행 인원은 최소 1명 이상이어야 합니다.")
    private Integer travelerCount;

    // 추가적으로 예약자 정보 등을 여기에 추가할 수 있습니다.
}