package com.mypost.exception.advice;

import com.mypost.controller.dto.response.ErrorResponse;
import com.mypost.controller.dto.response.ValidationErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class MethodArgumentNotValidAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse("400", "잘못된 요청입니다.");
        ex.getFieldErrors()
                .forEach(fieldError -> errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
