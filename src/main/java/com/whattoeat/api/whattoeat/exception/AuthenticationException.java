package com.whattoeat.api.whattoeat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException{

    public AuthenticationException(Exception cause){
        super(cause);
    }

    public AuthenticationException() {
        super();
    }

}
