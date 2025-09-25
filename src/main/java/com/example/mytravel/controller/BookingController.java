package com.example.mytravel.controller;

import com.example.mytravel.domain.PackageBooking;
import com.example.mytravel.dto.BookingFormDto;
import com.example.mytravel.dto.BookingResponseDto;
import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.dto.PaymentVerificationDto;
import com.example.mytravel.service.BookingService;
import com.example.mytravel.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final PackageService packageService;

    // 1. 예약 폼 페이지 보여주기
    @PostMapping("/form")
    public String bookingForm(@RequestParam("packageId") Long packageId,
                              @RequestParam("departureDate") LocalDate departureDate,
                              @RequestParam("travelerCount") Integer travelerCount,
                              Model model) {

        // 1. 파라미터로 직접 받은 값들을 사용하여 DTO를 새로 생성합니다.
        BookingFormDto bookingInfo = new BookingFormDto();
        bookingInfo.setPackageId(packageId);
        bookingInfo.setDepartureDate(departureDate);
        bookingInfo.setTravelerCount(travelerCount);

        // 2. DTO에 담겨온 packageId를 사용하여 상품 정보를 조회합니다.
        PackageListDto packageInfo = packageService.findPackageForBooking(bookingInfo.getPackageId());

        // 3. 조회된 상품 가격과 인원수를 바탕으로 총 가격을 계산합니다.
        int totalPrice = packageInfo.getPrice() * bookingInfo.getTravelerCount();

        // 4. HTML 템플릿에 필요한 모든 데이터를 모델에 담아 전달합니다.
        model.addAttribute("bookingInfo", bookingInfo);   // 새로 생성한 예약 정보 DTO
        model.addAttribute("packageInfo", packageInfo); // 조회된 상품 상세 정보
        model.addAttribute("totalPrice", totalPrice);   // 계산된 총 가격

        return "booking/form";
    }

    // 2. 결제 전, 사전 예약 정보 생성 API (AJAX 호출용)
    @PostMapping("/prepare")
    @ResponseBody
    public ResponseEntity<BookingResponseDto> prepareBooking(@RequestBody BookingFormDto dto,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        // 1. 서비스는 엔티티를 그대로 반환
        PackageBooking pendingBooking = bookingService.createPendingBooking(dto, userDetails.getUsername());

        // 2. 컨트롤러에서 엔티티를 DTO로 변환하여 응답
        BookingResponseDto responseDto = new BookingResponseDto(pendingBooking);

        return ResponseEntity.ok(responseDto);
    }

    // 3. 결제 후, 결제 정보 검증 API (AJAX 호출용)
    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationDto dto) {
        try {
            bookingService.verifyAndProcessPayment(dto.getImpUid(), dto.getMerchantUid());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 결제 실패 또는 위변조 시, 오류 응답
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. 예약 성공 페이지
    @GetMapping("/success")
    public String successPage() {
        return "booking/success";
    }
}