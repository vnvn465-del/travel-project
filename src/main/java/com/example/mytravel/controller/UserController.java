package com.example.mytravel.controller;
import com.example.mytravel.dto.UserSignUpDto;
import com.example.mytravel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller @RequestMapping("/users") @RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("userSignUpDto", new UserSignUpDto());
        return "signup";
    }
    @PostMapping("/signup")
    public String signUp(@Valid UserSignUpDto userSignUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return "signup"; }
        try { userService.signUp(userSignUpDto); } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "email.duplicate", e.getMessage());
            return "signup";
        }
        return "redirect:/users/login";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}