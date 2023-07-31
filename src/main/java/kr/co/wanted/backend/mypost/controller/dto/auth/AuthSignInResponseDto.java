package kr.co.wanted.backend.mypost.controller.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthSignInResponseDto {
    private final String accessToken;
}
