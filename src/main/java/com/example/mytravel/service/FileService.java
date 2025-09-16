package com.example.mytravel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    // application.yml에 설정된 파일 저장 경로를 주입받습니다.
    // 예: uploadPath: C:/mytravel/images/
    @Value("${uploadPath}")
    private String uploadPath;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        // UUID를 사용하여 파일명이 겹치지 않도록 고유한 파일명 생성
        String savedFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        File file = new File(uploadPath + savedFilename);
        multipartFile.transferTo(file);

        // 데이터베이스에 저장될 파일의 웹 접근 경로를 반환합니다.
        // 예: /images/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx_dest-jeju.jpg
        return "/images/" + savedFilename;
    }
}