package com.enterpriseassistant.user.exception;

public class InvalidUpdate extends RuntimeException {
    public InvalidUpdate(){
        super("Can not update user data");
    }
}
