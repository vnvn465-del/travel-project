package com.example.mytravel.controller;
import com.example.mytravel.dto.ReservationRequestDto;
import com.example.mytravel.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller @RequestMapping("/reservations") @RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping("/create")
    public String createReservation(@Valid ReservationRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/users/login"; }
        String userEmail = userDetails.getUsername();
        reservationService.createReservation(requestDto, userEmail);
        return "redirect:/"; // 예약 성공 후 메인 페이지로 이동 (향후 마이페이지로 변경 가능)
    }
}