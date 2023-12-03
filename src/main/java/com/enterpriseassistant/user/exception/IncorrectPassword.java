package com.enterpriseassistant.user.exception;

public class IncorrectPassword extends RuntimeException {
    public IncorrectPassword(){
        super("Password is incorrect");
    }
}
