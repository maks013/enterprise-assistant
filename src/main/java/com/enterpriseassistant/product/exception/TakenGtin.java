package com.enterpriseassistant.product.exception;

public class TakenGtin extends RuntimeException{
    public TakenGtin(){
        super("Product with this gtin number already exists");
    }
}
