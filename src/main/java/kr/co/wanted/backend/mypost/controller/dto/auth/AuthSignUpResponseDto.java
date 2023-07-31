package kr.co.wanted.backend.mypost.controller.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthSignUpResponseDto {
    private final Long memberId;
}
