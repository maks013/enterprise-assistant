package com.enterpriseassistant.user.exception;

public class NotActiveAccount extends RuntimeException {
    public NotActiveAccount() {
        super("Account is not active");
    }
}
