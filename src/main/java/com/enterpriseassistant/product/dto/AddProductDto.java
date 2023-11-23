package com.enterpriseassistant.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AddProductDto {

    private final String gtin;
    private final String name;
    private final BigDecimal priceNet;
    private final String additionalInformation;

}
