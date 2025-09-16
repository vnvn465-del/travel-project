package com.example.mytravel.controller;

import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PackageService packageService;

    @GetMapping("/")
    public String mainPage(Model model) {
        // 서비스에서 인기 상품과 일반 상품 목록을 조회
        List<PackageListDto> popularPackages = packageService.findPopularPackages();
        List<PackageListDto> normalPackages = packageService.findNormalPackages();

        // 모델에 담아 View(HTML)로 전달
        model.addAttribute("popularPackages", popularPackages);
        model.addAttribute("normalPackages", normalPackages);

        // templates/main.html (또는 index.html)을 렌더링
        return "index";
    }
}