package com.aloydev.weighttrackerapi.weighttrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WtAuthException extends RuntimeException{

    public WtAuthException(String message) {
        super(message);
    }
}