package com.enterpriseassistant.user.exception;

public class InvalidUserId extends RuntimeException {
    public InvalidUserId(){
        super("Can not reach user with that id");
    }
}
