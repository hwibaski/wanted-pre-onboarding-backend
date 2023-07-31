package kr.co.wanted.backend.mypost.controller.auth;

import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignInRequestDto;
import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignInResponseDto;
import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignUpRequestDto;
import kr.co.wanted.backend.mypost.controller.dto.auth.AuthSignUpResponseDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.security.jwt.TokenProvider;
import kr.co.wanted.backend.mypost.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public AuthSignUpResponseDto signUp(@Valid @RequestBody AuthSignUpRequestDto requestDto) {
        Member member = authService.signUp(requestDto);

        return new AuthSignUpResponseDto(member.getId());
    }

    @PostMapping("/signin")
    public AuthSignInResponseDto signIn(@Valid @RequestBody AuthSignInRequestDto requestBody) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        return new AuthSignInResponseDto(jwt);
    }
}
