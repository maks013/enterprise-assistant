package com.enterpriseassistant.order.exception;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound(){
        super("Order not found");
    }
}
