package com.enterpriseassistant.client.exception;

public class AddressNotFound extends RuntimeException {
    public AddressNotFound() {
        super("Address not found");
    }
}
