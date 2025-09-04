package com.example.mytravel.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Getter @Setter
public class ReservationRequestDto {
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long packageId;
    @NotNull(message = "예약 날짜는 필수입니다.") @Future(message = "예약 날짜는 오늘 이후여야 합니다.")
    private LocalDate reservationDate;
    @NotNull(message = "여행 인원은 필수입니다.") @Min(value = 1, message = "여행 인원은 최소 1명 이상이어야 합니다.")
    private int numberOfTravelers;
}