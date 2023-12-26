package com.enterpriseassistant.client.exception;

public class RepresentativeNotFound extends RuntimeException{
    public RepresentativeNotFound(){
        super("Representative not found");
    }
}
