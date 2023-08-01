package com.mypost.exception.advice;

import com.mypost.controller.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Integer.MAX_VALUE - 1)
public class RuntimeExceptionAdvice {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("400", ex.getMessage()));
    }
}
