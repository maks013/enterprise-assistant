package com.enterpriseassistant.infrastructure.email;

public interface EmailSender {
    void sendWithAttachment(String to, String clientName, byte[] attachment, String invoiceNumber);
}
