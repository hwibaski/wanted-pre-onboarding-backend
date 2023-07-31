package kr.co.wanted.backend.mypost.controller.dto.auth;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class AuthSignUpRequestDto {
    @NotBlank(message = "이메일 입력해주세요")
    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이여야합니다.")
    private String password;

    public AuthSignUpRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
