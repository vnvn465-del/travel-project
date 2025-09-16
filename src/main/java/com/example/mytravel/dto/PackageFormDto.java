package com.example.mytravel.dto;

import com.example.mytravel.domain.enums.PackageStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PackageFormDto {

    private Long id; // 상품 수정 시 필요한 ID

    @NotBlank(message = "상품명은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "국가는 필수 입력 항목입니다.")
    private String country;

    @NotBlank(message = "도시는 필수 입력 항목입니다.")
    private String city;

    @NotBlank(message = "상품 설명은 필수 입력 항목입니다.")
    private String description;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotBlank(message = "여행 기간은 필수 입력 항목입니다.")
    private String travelPeriod;

    private LocalDate departureDateStart;
    private LocalDate departureDateEnd;

    @Min(value = 1, message = "최소 출발 인원은 1명 이상이어야 합니다.")
    private Integer minTravelers;
    private Integer maxTravelers;

    @NotNull(message = "상품 상태는 필수 선택 항목입니다.")
    private PackageStatus status;

    private boolean isPopular;

    // 상품 등록 시 받을 이미지 파일 리스트 (최대 5개 등 제한 가능)
    private List<MultipartFile> imageFiles = new ArrayList<>();

    // 상품 수정 시 기존 이미지 ID 리스트 (삭제할 이미지 관리에 사용 가능)
    private List<Long> originalImageIds = new ArrayList<>();

    // 상세 일정 정보를 담을 DTO 리스트
    private List<ItineraryFormDto> itineraries = new ArrayList<>();

}