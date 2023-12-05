package com.enterpriseassistant.user.exception;

public class TakenUsername extends RuntimeException{
    public TakenUsername(){
        super("This username is already taken");
    }
}
