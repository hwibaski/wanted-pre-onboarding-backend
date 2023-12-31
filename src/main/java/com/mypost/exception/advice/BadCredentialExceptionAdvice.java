package com.mypost.exception.advice;

import com.mypost.controller.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(3)
public class BadCredentialExceptionAdvice {
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("401", "이메일과 비밀번호를 확인해주세요"));
    }
}
