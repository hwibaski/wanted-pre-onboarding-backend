package com.mypost.exception.advice;

import com.mypost.controller.dto.response.ErrorResponse;
import com.mypost.exception.CustomException;
import com.mypost.exception.UnathorizedException;
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

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnathorizedException(UnathorizedException ex) {
        return ResponseEntity
                .status(401)
                .body(new ErrorResponse("401", ex.getMessage()));
    }
}
