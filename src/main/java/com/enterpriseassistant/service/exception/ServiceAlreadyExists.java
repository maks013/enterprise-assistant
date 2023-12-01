package com.enterpriseassistant.service.exception;

public class ServiceAlreadyExists extends RuntimeException {
    public ServiceAlreadyExists(){
        super("Service with this name already exists");
    }
}
