package com.enterpriseassistant.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductOrderItemDto {

    private final Integer id;
    private final Integer orderId;
    private final String name;
    private final BigDecimal unitPriceGross;
    private final BigDecimal unitPriceNet;
    private final Integer quantity;
    private final Integer productId;

}
