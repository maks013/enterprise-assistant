package com.enterpriseassistant.invoice.domain;

import com.enterpriseassistant.invoice.dto.InvoiceDto;
import com.enterpriseassistant.order.domain.Payment;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String payment;
    private LocalDateTime paymentDueDate;
    private BigDecimal totalPriceGross;
    private BigDecimal totalPriceNet;
    private LocalDateTime issueDate;
    private Integer orderId;

    InvoiceDto toDto() {
        return InvoiceDto.builder()
                .id(id)
                .number(number)
                .payment(payment)
                .paymentDueDate(paymentDueDate)
                .totalPriceGross(getAsBigDecimal(totalPriceGross))
                .totalPriceNet(getAsBigDecimal(totalPriceNet))
                .issueDate(issueDate)
                .orderId(orderId)
                .build();
    }

    private BigDecimal getAsBigDecimal(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

}
