package com.example.mytravel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // ★★★★★ 이 부분이 가장 중요합니다. ★★★★★
                        // 로그인 페이지 자체는 누구나 접근할 수 있도록 허용해야 무한 루프가 발생하지 않습니다.
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers("/booking/**").authenticated()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/packages/**", "/users/signup").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/mypage/**").authenticated()
                        // 위에서 지정하지 않은 나머지 모든 경로는 인증이 필요합니다.
                        .anyRequest().authenticated()
                )
                // 2. 로그인 설정
                .formLogin(form -> form
                                .loginPage("/users/login") // 로그인 페이지 경로
                                .loginProcessingUrl("/users/login") // 로그인 처리 경로
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/users/login?error=true")
                        // formLogin 자체에는 permitAll()이 내장되어 있지만, authorizeHttpRequests에서 명시하는 것이 더 안전합니다.
                )
                // 3. 로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                )
                // 4. CSRF 설정
                .csrf(csrf -> csrf
                        // 문제가 발생하는 /booking/prepare 와 /booking/verify 경로에 대해서만 CSRF 보호를 비활성화합니다.
                        .ignoringRequestMatchers("/booking/prepare", "/booking/verify")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}