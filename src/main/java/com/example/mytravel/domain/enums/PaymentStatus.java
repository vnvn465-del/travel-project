package com.example.mytravel.domain.enums;

public enum PaymentStatus {
    SUCCESS,        // 결제 성공
    FAILED,         // 결제 실패 (카드 한도 초과, 비밀번호 오류 등)
    CANCELLED       // 결제 취소 (환불 완료)
}