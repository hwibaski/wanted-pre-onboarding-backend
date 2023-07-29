package kr.co.wanted.backend.mypost.exception.advice;

import kr.co.wanted.backend.mypost.controller.dto.ResponseTemplate.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MethodArgumentNotValidAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseTemplate> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        System.out.println(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        ResponseTemplate restTemplate = ResponseTemplate.failResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(restTemplate, HttpStatus.BAD_REQUEST);
    }
}
