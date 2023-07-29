package kr.co.wanted.backend.mypost.controller.dto.ResponseTemplate;

import lombok.Getter;

@Getter
public class ResponseTemplate {
    private boolean success;
    private String message;
    private Object data;

    private ResponseTemplate(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private ResponseTemplate(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public static ResponseTemplate successResponse(Object data) {
        return new ResponseTemplate(true, data);
    }

    public static ResponseTemplate failResponse(String message) {
        return new ResponseTemplate(false, message);
    }
}
