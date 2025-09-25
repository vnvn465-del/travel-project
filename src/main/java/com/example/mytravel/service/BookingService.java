package com.example.mytravel.service;

import com.example.mytravel.domain.*;

import com.example.mytravel.domain.enums.BookingStatus;
import com.example.mytravel.dto.BookingFormDto;
import com.example.mytravel.repository.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TravelPackageRepository travelPackageRepository;
    private final PaymentRepository paymentRepository;

    private IamportClient iamportClient;

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    // 1. 사전 예약 정보 생성 (결제 대기 상태)
    public PackageBooking createPendingBooking(BookingFormDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        TravelPackage travelPackage = travelPackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new EntityNotFoundException("여행 상품을 찾을 수 없습니다."));

        int totalPrice = travelPackage.getPrice() * dto.getTravelerCount();
        String bookingNumber = "MT-" + LocalDate.now().toString().replace("-", "") + "-" + UUID.randomUUID().toString().substring(0, 8);

        PackageBooking booking = PackageBooking.builder()
                .user(user)
                .travelPackage(travelPackage)
                .bookingNumber(bookingNumber)
                .bookingDate(LocalDateTime.now())
                .totalPrice(totalPrice)
                .status(BookingStatus.PAYMENT_PENDING)
                .departureDate(dto.getDepartureDate())
                .travelerCount(dto.getTravelerCount())
                .build();

        return bookingRepository.save(booking);
    }

    // 2. 결제 검증 및 예약 확정
    public com.example.mytravel.domain.Payment verifyAndProcessPayment(String impUid, String merchantUid) {
        try {
            // 아임포트의 Payment 클래스는 그대로 사용
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);
            com.siot.IamportRestClient.response.Payment paymentInfo = iamportResponse.getResponse();

            Booking booking = bookingRepository.findByBookingNumber(merchantUid)
                    .orElseThrow(() -> new EntityNotFoundException("예약 정보를 찾을 수 없습니다."));

            if (paymentInfo.getAmount().intValue() != booking.getTotalPrice()) {
                throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
            }

            booking.confirmBooking();

            // ★★★★★ 우리 엔티티를 사용할 때는 패키지 전체 경로를 명시합니다. ★★★★★
            com.example.mytravel.domain.Payment paymentEntity = com.example.mytravel.domain.Payment.builder()
                    .booking(booking)
                    .pgTransactionId(impUid)
                    .paymentMethod(paymentInfo.getPayMethod())
                    .amount(paymentInfo.getAmount().intValue())
                    .status(com.example.mytravel.domain.enums.PaymentStatus.SUCCESS)
                    .paymentDate(LocalDateTime.now())
                    .build();

            return paymentRepository.save(paymentEntity);

        } catch (Exception e) {
            throw new RuntimeException("결제 검증 중 오류 발생: " + e.getMessage(), e);
        }
    }
}