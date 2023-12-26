package com.enterpriseassistant.order.exception;

public class InvalidOrderCreation extends RuntimeException {
    public InvalidOrderCreation(){
        super("Order must have at least one item");
    }
}
