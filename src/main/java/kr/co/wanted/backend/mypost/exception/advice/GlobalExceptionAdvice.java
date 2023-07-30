package kr.co.wanted.backend.mypost.exception.advice;

import kr.co.wanted.backend.mypost.controller.dto.Response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class GlobalExceptionAdvice {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse("500", "INTERNAL_SERVER_ERROR");

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
