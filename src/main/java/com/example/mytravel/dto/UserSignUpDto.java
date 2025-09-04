package com.example.mytravel.dto; // DTO 패키지 경로는 실제 프로젝트에 맞게 수정하세요.

import com.example.mytravel.domain.Role;
import com.example.mytravel.domain.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder import

import java.time.LocalDate;

@Getter
@Setter
public class UserSignUpDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String passwordConfirm;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private LocalDate birthdate;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "'-' 없이 10~11자리의 숫자만 입력해주세요.")
    private String phoneNumber;

    // 주소 관련 필드는 Validation이 필요하다면 추가하세요.
    private String address;
    private String addressDetail;
    // 우편번호 필드는 HTML에 있지만 User 엔티티에는 없으므로, DTO에만 존재하게 됩니다.
    private String postcode;

    // =================================================================
    // ============== 아래 toEntity 메소드를 추가해주세요 ==============
    // =================================================================
    /**
     * DTO의 정보를 바탕으로 User 엔티티를 생성합니다.
     * 이때, 비밀번호는 PasswordEncoder를 통해 암호화됩니다.
     * @param passwordEncoder 비밀번호 암호화를 위한 인코더
     * @return 생성된 User 엔티티
     */
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                // ★★★ 중요: 비밀번호는 반드시 암호화해서 저장해야 합니다.
                .password(passwordEncoder.encode(this.password))
                .birthdate(this.birthdate)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .addressDetail(this.addressDetail)
                // 회원가입 시 기본 권한을 USER로 설정합니다.
                .role(Role.USER)
                .build();
    }
}
