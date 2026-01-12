package com.example.mytravel.dto;

import com.example.mytravel.domain.PackageBooking;
import com.example.mytravel.domain.Payment;
import lombok.Getter;

// 필요에 따라 상세 페이지에 더 많은 정보를 추가할 수 있습니다.
@Getter
public class MyBookingDetailDto {

    // MyBookingListDto의 모든 정보를 포함 (상속을 사용하거나 필드를 그대로 가져옴)
    private final MyBookingListDto bookingInfo;
    private final PaymentInfo paymentInfo; // 결제 정보 DTO

    public MyBookingDetailDto(PackageBooking booking) {
        this.bookingInfo = new MyBookingListDto(booking);
        // 결제 정보가 있을 경우에만 PaymentInfo 객체 생성
        this.paymentInfo = booking.getPayment() != null ? new PaymentInfo(booking.getPayment()) : null;
    }

    @Getter
    private static class PaymentInfo {
        private final String paymentMethod;
        private final int amount;
        private final String pgTransactionId;

        public PaymentInfo(Payment payment) {
            this.paymentMethod = payment.getPaymentMethod();
            this.amount = payment.getAmount();
            this.pgTransactionId = payment.getPgTransactionId();
        }
    }
}