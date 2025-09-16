package com.example.mytravel.controller;

import com.example.mytravel.dto.PackageDetailDto;
import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    // 전체 상품 목록 페이지
    @GetMapping
    public String packageListPage(Model model) {
        List<PackageListDto> packages = packageService.findAllPackages();
        model.addAttribute("packages", packages);
        return "packages/list"; // templates/packages/list.html 렌더링
    }

    // 상품 상세 페이지
    @GetMapping("/{packageId}")
    public String packageDetailPage(@PathVariable("packageId") Long packageId, Model model) {
        PackageDetailDto packageDetail = packageService.findPackageDetail(packageId);
        model.addAttribute("package", packageDetail);
        return "packages/detail"; // templates/packages/detail.html 렌더링
    }
}