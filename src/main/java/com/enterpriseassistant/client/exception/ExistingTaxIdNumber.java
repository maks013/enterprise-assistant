package com.enterpriseassistant.client.exception;

public class ExistingTaxIdNumber extends RuntimeException {
    public ExistingTaxIdNumber(){
        super("Given tax id number already exists");
    }
}
