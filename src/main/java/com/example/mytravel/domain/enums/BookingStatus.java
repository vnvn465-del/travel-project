package com.example.mytravel.domain.enums;

public enum BookingStatus {
    PAYMENT_PENDING, // 결제 대기중: 사용자가 예약을 시작했지만 아직 결제하지 않은 상태
    CONFIRMED,       // 예약 확정: 결제가 성공적으로 완료되어 예약이 확정된 상태
    CANCELLED        // 예약 취소: 사용자가 직접 또는 관리자에 의해 예약이 취소된 상태
}