package com.mypost.security;

import com.mypost.annotation.WithAccount;
import com.mypost.controller.dto.auth.AuthSignUpRequestDto;
import com.mypost.service.auth.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final CustomUserDetailService customUserDetailService;
    private final AuthService authService;

    public WithAccountSecurityContextFactory(CustomUserDetailService customUserDetailService, AuthService authService) {
        this.customUserDetailService = customUserDetailService;
        this.authService = authService;
    }

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String nickname = withAccount.value();

        AuthSignUpRequestDto signUpForm = new AuthSignUpRequestDto("test@gmail.com", "12345678");

        authService.signUp(signUpForm);

        UserDetails principal = customUserDetailService.loadUserByUsername(nickname);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal
                , principal.getPassword()
                , principal.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
