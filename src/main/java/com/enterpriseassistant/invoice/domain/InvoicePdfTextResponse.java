package com.enterpriseassistant.invoice.domain;

enum InvoicePdfTextResponse {

    INVOICE_NUMBER("Faktura nr: "),
    ISSUE_DATE("Data wystawienia: "),
    SALE_DATE("Data sprzedaży: "),
    SELLER("Sprzedawca: "),
    BANK_ACCOUNT_NUMBER("Nr konta: "),
    TAX_ID("NIP: "),
    ADDRESS("Adres: "),
    VAT_PERCENTAGE("23%"),
    NUMBER("Lp."),
    PRODUCT_SERVICE_NAME("Nazwa towaru/usługi"),
    QUANTITY("Ilość"),
    NET_PRICE("Cena netto"),
    BUYER("Nabywca: "),
    VAT("VAT"),
    NET_AMOUNT("Kwota netto"),
    VAT_AMOUNT("Kwota vat"),
    GROSS_AMOUNT("Kwota brutto"),
    CURRENCY(" PLN"),
    PAYMENT_METHOD("Sposób zapłaty: "),
    PAYMENT_DATE("Termin zapłaty: "),
    DAYS(" dni"),
    NET_VALUE("Wartość netto: "),
    GROSS_VALUE("Wartość brutto: "),
    TO_PAY("Razem do zapłaty: "),
    TRANSFER_METHOD("przelew"),
    CASH_METHOD("płatność gotówką");

    final String info;

    InvoicePdfTextResponse(String info) {
        this.info = info;
    }
}
