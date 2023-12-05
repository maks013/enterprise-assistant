package com.enterpriseassistant.client.exception;

public class InvalidTaxIdFormat extends RuntimeException {
    public InvalidTaxIdFormat(){
        super("Invalid format of tax id number");
    }
}
