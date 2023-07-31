package kr.co.wanted.backend.mypost.service.auth;

import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignUpRequestDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.exception.AlreadyExistUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    AuthService authService;

    @Transactional
    @DisplayName("회원가입 성공 test")
    @Test
    void memberSignUpSuccess() {
        // given
        String email = "khmin3011@naver.com";
        String password = "12345678";
        AuthSignUpRequestDto authSignUpRequestDto = new AuthSignUpRequestDto(email, password);

        // when
        Member member = authService.signUp(authSignUpRequestDto);

        // then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isNotNull();
    }

    @Transactional
    @DisplayName("회원가입 성공 fail - email 중복")
    @Test
    void memberSignUpFailWhenEmailDuplicated() {
        // given
        String email = "khmin3011@naver.com";
        String password = "12345678";
        AuthSignUpRequestDto authSignUpRequestDto = new AuthSignUpRequestDto(email, password);
        authService.signUp(authSignUpRequestDto);

        // when
        // then
        assertThatThrownBy(
                () -> authService.signUp(authSignUpRequestDto))
                .isInstanceOf(AlreadyExistUserException.class)
                .hasMessage("이미 존재하는 이메일입니다");
    }
}
