package com.example.mytravel.controller;

import com.example.mytravel.dto.MyBookingDetailDto;
import com.example.mytravel.dto.MyBookingListDto;
import com.example.mytravel.service.BookingService;
import jakarta.servlet.http.HttpServletRequest; // HttpServletRequest import
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final BookingService bookingService;

    // 마이페이지 기본 경로는 예약 내역으로 리다이렉션
    @GetMapping("")
    public String myPageRoot() {
        return "redirect:/users/mypage/bookings";
    }

    // 나의 예약 내역 목록 페이지
    @GetMapping("/bookings")
    public String myBookingListPage(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                    HttpServletRequest request) { // ★★★ HttpServletRequest 파라미터 추가 ★★★
        List<MyBookingListDto> bookings = bookingService.findMyBookings(userDetails.getUsername());
        model.addAttribute("bookings", bookings);

        // ★★★ 현재 URL을 모델에 직접 담아서 전달합니다. ★★★
        model.addAttribute("currentUrl", request.getRequestURI());

        return "users/mypage/bookings";
    }

    // 예약 상세 정보 페이지
    @GetMapping("/bookings/{bookingId}")
    public String myBookingDetailPage(@PathVariable Long bookingId, Model model,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      HttpServletRequest request) { // ★★★ HttpServletRequest 파라미터 추가 ★★★
        try {
            MyBookingDetailDto bookingDetail = bookingService.findMyBookingDetail(bookingId, userDetails.getUsername());
            model.addAttribute("bookingDetail", bookingDetail);

            // ★★★ 현재 URL을 모델에 직접 담아서 전달합니다. ★★★
            model.addAttribute("currentUrl", request.getRequestURI());

            return "users/mypage/booking-detail";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
}