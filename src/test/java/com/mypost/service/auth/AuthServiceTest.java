package com.mypost.service.auth;

import com.mypost.controller.dto.auth.AuthSignUpRequestDto;
import com.mypost.domain.member.Member;
import com.mypost.exception.AlreadyExistUserException;
import com.mypost.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        this.memberRepository.deleteAllInBatch();
    }

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
