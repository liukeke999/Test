package com.example.demo.exception;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException(){}

    public UnAuthenticationException(String data) {
        super(data);
    }

}
