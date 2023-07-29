package kr.co.wanted.backend.mypost.controller.dto.member;

import kr.co.wanted.backend.mypost.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class MemberSignUpRequestDto {
    @NotBlank(message = "이메일 입력해주세요")
    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이여야합니다.")
    private String password;

    public MemberSignUpRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    @Builder
    public Member toEntity() {
        return Member.createMember(email, password);
    }
}
