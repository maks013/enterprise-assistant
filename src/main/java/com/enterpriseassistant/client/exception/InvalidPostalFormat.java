package com.enterpriseassistant.client.exception;

public class InvalidPostalFormat extends RuntimeException {
    public InvalidPostalFormat(){
        super("Invalid format of postal code");
    }
}
