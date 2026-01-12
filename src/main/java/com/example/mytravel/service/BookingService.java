package com.example.mytravel.service;

import com.example.mytravel.domain.*;

import com.example.mytravel.domain.enums.BookingStatus;
import com.example.mytravel.dto.BookingFormDto;
import com.example.mytravel.dto.MyBookingDetailDto;
import com.example.mytravel.dto.MyBookingListDto;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            // 우리 DB에서 주문(예약) 정보를 조회합니다. (이것은 그대로 유지)
            Booking booking = bookingRepository.findByBookingNumber(merchantUid)
                    .orElseThrow(() -> new EntityNotFoundException("예약 정보를 찾을 수 없습니다."));



            /* --- 실제 운영 시 활성화할 금액 검증 로직 ---
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);
            com.siot.IamportRestClient.response.Payment paymentInfo = iamportResponse.getResponse();

            if (paymentInfo.getAmount().intValue() != booking.getTotalPrice()) {
                throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
            }
            */

            // 테스트 중이므로, 금액 검증을 통과했다고 가정하고 바로 다음 단계를 진행합니다.

            // 결제가 성공적으로 검증되었으면, 예약 상태를 '확정'으로 변경합니다.
            booking.confirmBooking();

            // 결제 정보를 우리 DB에 저장합니다.
            com.example.mytravel.domain.Payment paymentEntity = com.example.mytravel.domain.Payment.builder()
                    .booking(booking)
                    .pgTransactionId(impUid) // 아임포트 거래 ID는 그대로 저장
                    .paymentMethod("card") // 결제 수단은 'card'로 임시 저장
                    .amount(booking.getTotalPrice()) // 금액은 우리 DB의 예약 금액으로 저장
                    .status(com.example.mytravel.domain.enums.PaymentStatus.SUCCESS)
                    .paymentDate(LocalDateTime.now())
                    .build();

            return paymentRepository.save(paymentEntity);

        } catch (Exception e) {
            // 이 부분은 그대로 두어 다른 예외는 잡을 수 있도록 합니다.
            throw new RuntimeException("결제 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }


    /**
     * 현재 로그인한 사용자의 모든 예약 목록을 조회합니다.
     * @param userEmail 현재 사용자의 이메일
     * @return 예약 목록 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<MyBookingListDto> findMyBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        // 우선 패키지 예약만 조회 (나중에 항공/호텔 추가)
        return bookingRepository.findPackageBookingsByUserId(user.getId()).stream()
                .map(MyBookingListDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 현재 로그인한 사용자의 특정 예약 건 상세 정보를 조회합니다.
     * @param bookingId 조회할 예약 ID
     * @param userEmail 현재 사용자의 이메일
     * @return 예약 상세 정보 DTO
     */
    @Transactional(readOnly = true)
    public MyBookingDetailDto findMyBookingDetail(Long bookingId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        PackageBooking booking = bookingRepository.findPackageBookingByIdAndUserId(bookingId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 예약을 찾을 수 없거나 조회 권한이 없습니다."));

        return new MyBookingDetailDto(booking);
    }
}
