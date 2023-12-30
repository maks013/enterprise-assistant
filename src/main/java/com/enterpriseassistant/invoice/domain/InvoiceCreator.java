package com.enterpriseassistant.invoice.domain;

import com.enterpriseassistant.client.dto.ClientDto;
import com.enterpriseassistant.order.domain.Payment;
import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.dto.ProductOrderItemDto;
import com.enterpriseassistant.order.dto.ServiceOrderItemDto;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static com.enterpriseassistant.invoice.domain.InvoicePdfTextResponse.*;

@Component
class InvoiceCreator {

    @Value("${company.name}")
    private String companyName;

    @Value("${company.address}")
    private String companyAddress;

    @Value("${company.bankAccount.number}")
    private String companyBankAccountNumber;

    @Value("${company.taxId.number}")
    private String companyTaxIdNumber;

    private static final String FONT_PATH = "src/main/resources/fonts/Lato-Regular.ttf";

    byte[] generateInvoicePdf(Invoice invoice, OrderDto order, ClientDto client) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH);
        } catch (IOException | java.io.IOException e) {
            throw new RuntimeException("Could not load font for PDF", e);
        }

        document.add(new Paragraph(INVOICE_NUMBER.info + invoice.getNumber()).setFont(font).setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(ISSUE_DATE.info + invoice.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(SALE_DATE.info + order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(SELLER.info).setFont(font).setBold());
        document.add(new Paragraph(companyName).setFont(font));
        document.add(new Paragraph(ADDRESS.info + companyAddress).setFont(font));;
        document.add(new Paragraph(TAX_ID.info + companyTaxIdNumber).setFont(font));
        document.add(new Paragraph(BANK_ACCOUNT_NUMBER.info + companyBankAccountNumber).setFont(font));

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(BUYER.info).setFont(font).setBold());
        document.add(new Paragraph(client.getCompanyName()).setFont(font));
        document.add(new Paragraph(ADDRESS.info + client.getAddress().getStreet() + ", " +
                client.getAddress().getPostalCode() + " " + client.getAddress().getCity()).setFont(font));
        document.add(new Paragraph(TAX_ID.info + client.getTaxIdNumber()).setFont(font));

        document.add(new Paragraph("\n"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 1, 2, 2, 2}))
                .useAllAvailableWidth();

        table.addHeaderCell(new Cell().add(new Paragraph(NUMBER.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(PRODUCT_SERVICE_NAME.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(QUANTITY.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(NET_PRICE.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(VAT.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(NET_AMOUNT.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(VAT_AMOUNT.info).setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph(GROSS_AMOUNT.info).setFont(font)).setBold());

        int lineNo = 1;
        for (ProductOrderItemDto product : order.getProductOrderItems()) {
            lineNo = getLineNo(font, table, lineNo, product.getName(), product.getQuantity(), product.getUnitPriceNet(), product.getUnitPriceGross());
        }
        for (ServiceOrderItemDto service : order.getServiceOrderItems()) {
            lineNo = getLineNo(font, table, lineNo, service.getName(), service.getQuantity(), service.getUnitPriceNet(), service.getUnitPriceGross());
        }

        document.add(table);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(PAYMENT_METHOD.info + getPaymentMethod(invoice.getPayment()))
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(PAYMENT_DATE.info + order.getDaysToPay().days + DAYS.info + "\n" + invoice.getIssueDate().plusDays(order.getDaysToPay().days).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(NET_VALUE.info + invoice.getTotalPriceNet().toString() + CURRENCY.info)
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(GROSS_VALUE.info + invoice.getTotalPriceGross().toString() + CURRENCY.info)
                .setFont(font).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(TO_PAY.info + invoice.getTotalPriceGross().toString() + CURRENCY.info)
                .setFont(font).setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(16));

        document.close();
        return outputStream.toByteArray();
    }

    private int getLineNo(PdfFont font, Table table, int lineNo, String name, Integer quantity, BigDecimal unitPriceNet, BigDecimal unitPriceGross) {
        table.addCell(new Cell().add(new Paragraph(String.valueOf(lineNo++)).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(name).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(quantity)).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(unitPriceNet.toString()).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(VAT_PERCENTAGE.info).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(calculateTotalForUnit(unitPriceNet, quantity).toString()).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(calculateTotalForUnit(unitPriceGross, quantity).subtract(calculateTotalForUnit(unitPriceNet, quantity)).toString()).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(calculateTotalForUnit(unitPriceGross, quantity).toString()).setFont(font)));
        return lineNo;
    }

    private BigDecimal calculateTotalForUnit(BigDecimal price, Integer quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private String getPaymentMethod(String payment) {
        if (payment.equals(Payment.TRANSFER.toString())) {
            return TRANSFER_METHOD.info;
        } else {
            return CASH_METHOD.info;
        }
    }
}
