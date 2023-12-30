package com.enterpriseassistant.invoice.exception;

public class InvoiceAlreadyExists extends RuntimeException {
    public InvoiceAlreadyExists() {
        super("Invoice for this order already exists");
    }
}
