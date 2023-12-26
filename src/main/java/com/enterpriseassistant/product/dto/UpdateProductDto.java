package com.enterpriseassistant.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class UpdateProductDto {

    private final String gtin;
    private final String name;
    private final BigDecimal priceNet;
    private final String imageUrl;
    private final String additionalInformation;

}
