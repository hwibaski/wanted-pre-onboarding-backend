package com.mypost.exception;

public class AlreadyExistUserException extends CustomException {
    public AlreadyExistUserException(String message) {
        super(message);
    }
}
