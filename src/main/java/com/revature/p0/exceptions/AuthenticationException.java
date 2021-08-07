package com.revature.p0.exceptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message){
        super(message);
    }
}
