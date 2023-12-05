package com.enterpriseassistant.user.exception;

public class InvalidEmailFormat extends RuntimeException {
    public InvalidEmailFormat(){
        super("Format of email address is invalid");
    }
}
