package com.example.mytravel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerificationDto {
    private String impUid; // 아임포트에서 발급하는 결제 고유 ID
    private String merchantUid; // 우리가 생성한 주문(예약) 번호
}