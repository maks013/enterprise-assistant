package com.enterpriseassistant.product.exception;

public class InvalidNetPrice extends RuntimeException {
    public InvalidNetPrice(){
        super("Incorrect net price");
    }
}
