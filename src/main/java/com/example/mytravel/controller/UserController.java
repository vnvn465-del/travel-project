package com.example.mytravel.controller;

import com.example.mytravel.dto.UserSignUpDto;
import com.example.mytravel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userSignUpDto", new UserSignUpDto());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userSignUpDto") UserSignUpDto userSignUpDto,
                         BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/signup";
        }

        try {
            userService.saveUser(userSignUpDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/signup";
        }

        // 리다이렉트 페이지로 일회성 데이터를 전달합니다.
        // "signupSuccess"라는 이름으로 "회원가입이 완료되었습니다." 메시지를 보냅니다.
        redirectAttributes.addFlashAttribute("signupSuccess", "회원가입이 완료되었습니다. 로그인해주세요.");

        return "redirect:/users/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉션
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }


//    /**
//     * [테스트 전용] 로그인 정보를 직접 검증하는 API입니다.
//     * 브라우저에서 http://localhost:8080/users/verify?email=user@test.com&password=password1234
//     * 와 같이 호출하여 테스트할 수 있습니다.
//     */
//    @GetMapping("/verify")
//    @ResponseBody // HTML 페이지가 아닌, 텍스트 데이터를 직접 반환하도록 설정
//    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {
//        try {
//            // 1. UserService가 DB에서 사용자 정보를 가져오는지 테스트
//            UserDetails userDetails = userService.loadUserByUsername(email);
//            String dbPassword = userDetails.getPassword();
//
//            System.out.println("====== DEBUGGING ======");
//            System.out.println("입력된 이메일 (Input Email): " + email);
//            System.out.println("입력된 비밀번호 (Input Password): " + password);
//            System.out.println("DB에서 가져온 암호화된 비밀번호 (DB Password): " + dbPassword);
//            System.out.println("=====================");
//
//            // 2. 입력된 비밀번호와 DB의 암호화된 비밀번호가 일치하는지 테스트
//            if (passwordEncoder.matches(password, dbPassword)) {
//                System.out.println(">>> 비밀번호 일치! (Password MATCH!)");
//                return ResponseEntity.ok("사용자 인증 성공! [이메일: " + email + ", 권한: " + userDetails.getAuthorities() + "]");
//            } else {
//                System.out.println(">>> 비밀번호 불일치! (Password MISMATCH!)");
//                return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");
//            }
//        } catch (Exception e) {
//            System.out.println(">>> 사용자 조회 실패! (User NOT FOUND!)");
//            System.out.println("에러 메시지: " + e.getMessage());
//            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다: " + email);
//        }
//    }
}