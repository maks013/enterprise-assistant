package com.enterpriseassistant.invoice.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class InvoiceDto {

    private Integer id;
    private String number;
    private String payment;
    private LocalDateTime paymentDueDate;
    private BigDecimal totalPriceGross;
    private BigDecimal totalPriceNet;
    private LocalDateTime issueDate;
    private Integer orderId;

}
