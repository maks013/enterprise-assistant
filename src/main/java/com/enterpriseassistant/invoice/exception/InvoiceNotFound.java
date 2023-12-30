package com.enterpriseassistant.invoice.exception;

public class InvoiceNotFound extends RuntimeException {
    public InvoiceNotFound() {
        super("Invoice not found");
    }
}
