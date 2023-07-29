package kr.co.wanted.backend.mypost.exception.advice;

import kr.co.wanted.backend.mypost.controller.dto.ResponseTemplate.ResponseTemplate;
import kr.co.wanted.backend.mypost.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvice {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ResponseTemplate> handleCustomException(CustomException ex) {
        System.out.println("custom");
        System.out.println(ex.getMessage());

        ResponseTemplate restTemplate = ResponseTemplate.failResponse(ex.getMessage());

        return new ResponseEntity<>(restTemplate, HttpStatus.BAD_REQUEST);
    }
}
