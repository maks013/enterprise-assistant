package com.enterpriseassistant.service.exception;

public class ServiceNotFound extends RuntimeException{
    public ServiceNotFound(){
        super("Service not found");
    }
}
