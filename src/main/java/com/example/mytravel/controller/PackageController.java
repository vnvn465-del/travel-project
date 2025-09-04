package com.example.mytravel.controller;

import com.example.mytravel.dto.PackageDetailDto; // 2단계에서 생성할 DTO
import com.example.mytravel.service.PackageService; // 서비스 클래스
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    @GetMapping("/{packageId}")
    public String packageDetail(@PathVariable Long packageId, Model model) {
        // 서비스로부터 packageId에 해당하는 데이터를 DTO 형태로 받아옵니다.
        PackageDetailDto packageDto = packageService.getPackageDetail(packageId);

        // 모델에 담아서 View로 전달합니다.
        model.addAttribute("package", packageDto);

        // 보여줄 HTML 파일의 경로를 반환합니다.
        return "packages/detail";
    }
}