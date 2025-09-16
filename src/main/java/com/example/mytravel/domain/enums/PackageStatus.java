package com.example.mytravel.domain.enums;

public enum PackageStatus {
    ON_SALE,      // 판매중: 사용자에게 노출되고 예약 가능
    SOLD_OUT      // 판매종료: 사용자에게는 보이지만 예약은 불가능 (예: 마감)
}