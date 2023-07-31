package kr.co.wanted.backend.mypost.controller.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class AuthSignInRequestDto {
    @NotBlank(message = "이메일 입력해주세요")
    @Email(message = "이메일 형식을 확인해주세요")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이여야합니다.")
    private final String password;
}
