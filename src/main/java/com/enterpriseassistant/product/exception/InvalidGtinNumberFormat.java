package com.enterpriseassistant.product.exception;

public class InvalidGtinNumberFormat extends RuntimeException {
    public InvalidGtinNumberFormat(){
        super("Format of gtin number is incorrect");
    }
}
