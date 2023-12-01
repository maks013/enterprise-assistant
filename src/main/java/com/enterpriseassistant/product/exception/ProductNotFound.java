package com.enterpriseassistant.product.exception;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(){
        super("Product not found");
    }
}
