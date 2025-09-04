package com.example.mytravel.controller;
import com.example.mytravel.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller @RequiredArgsConstructor
public class HomeController {
    private final PackageService packageService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("popularPackages", packageService.findPopularPackages());
        model.addAttribute("normalPackages", packageService.findNormalPackages());
        model.addAttribute("packages", packageService.getAllPackages());
        return "index";
    }
}