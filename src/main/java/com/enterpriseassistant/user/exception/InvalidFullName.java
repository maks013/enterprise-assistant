package com.enterpriseassistant.user.exception;

public class InvalidFullName extends RuntimeException {
    public InvalidFullName(){
        super("Incorrect full name");
    }
}
