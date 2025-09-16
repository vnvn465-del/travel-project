package com.example.mytravel;

import com.example.mytravel.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args){
        SpringApplication.run(SpringBootApp.class, args);
    }

    /**
     * 애플리케이션 실행 후 특정 코드를 실행하고 싶을 때 ApplicationRunner를 사용합니다.
     * 여기서는 애플리케이션이 시작되면 UserService를 주입받아
     * 관리자 계정이 없으면 생성하도록 합니다.
     * @param userService 주입받을 UserService
     * @return ApplicationRunner 구현체
     */
    @Bean
    public ApplicationRunner applicationRunner(UserService userService) {
        return args -> {
            // 여기에 실행하고 싶은 코드를 작성합니다.
            userService.createAdminUserIfNotExists("admin@mytravel.com", "admin1234");
        };
    }
}

