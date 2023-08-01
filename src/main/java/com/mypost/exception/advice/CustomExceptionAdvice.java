package com.mypost.exception.advice;

import com.mypost.controller.dto.response.ErrorResponse;
import com.mypost.exception.CustomException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)
public class CustomExceptionAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("400", ex.getMessage()));
    }
}
