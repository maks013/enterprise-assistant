package com.enterpriseassistant.user.exception;

public class UserNotEnabled extends RuntimeException {
    public UserNotEnabled(){
        super("User not enabled");
    }
}
