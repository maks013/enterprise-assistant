package com.enterpriseassistant.order.exception;

public class InvalidStatusUpdate extends RuntimeException {
    public InvalidStatusUpdate(){
        super("Status update failed");
    }
}
