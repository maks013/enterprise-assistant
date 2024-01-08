package com.enterpriseassistant.infrastructure.email;

class EmailBuilder {

    public static String buildInvoiceEmail(String clientName, String invoiceNumber) {
        return "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333'>"
                + "<h2 style='color: #333; font-size: 20px;'>Faktura numer: " + invoiceNumber + "</h2>"
                + "<p>Dzień dobry " + clientName + ",</p>"
                + "<p>W załączeniu znajduje się faktura: " + invoiceNumber + ".</p>"
                + "<p>Z poważaniem,</p>"
                + "<p>Example Sp. z o.o.</p>"
                + "</div>";
    }

}
