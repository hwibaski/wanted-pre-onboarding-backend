package kr.co.wanted.backend.mypost.controller.dto.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private final Map<String, String> validation = new HashMap<>();

    public ValidationErrorResponse(String code, String message) {
        super(code, message);
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
