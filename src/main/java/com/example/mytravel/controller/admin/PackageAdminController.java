package com.example.mytravel.controller.admin;

import com.example.mytravel.dto.PackageFormDto;
import com.example.mytravel.dto.PackageListDto;
import com.example.mytravel.service.PackageAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/packages")
@RequiredArgsConstructor
public class PackageAdminController {

    private final PackageAdminService packageAdminService;

    // 상품 목록 페이지
    @GetMapping("")
    public String packageListPage(Model model) {
        List<PackageListDto> packages = packageAdminService.findPackages();
        model.addAttribute("packages", packages);
        return "admin/packages/list";
    }

    // 상품 등록 폼 페이지
    @GetMapping("/new")
    public String packageForm(Model model) {
        model.addAttribute("packageFormDto", new PackageFormDto());
        return "admin/packages/form";
    }

    // 상품 등록 처리
    @PostMapping("/new")
    public String createPackage(@Valid @ModelAttribute("packageFormDto") PackageFormDto dto,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/packages/form";
        }
        try {
            packageAdminService.savePackage(dto);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "admin/packages/form";
        }
        return "redirect:/admin/packages";
    }

    // (이하 상품 수정/삭제 컨트롤러 메소드는 필요시 추가 구현)
}