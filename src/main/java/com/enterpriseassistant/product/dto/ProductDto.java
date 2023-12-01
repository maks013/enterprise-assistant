package com.enterpriseassistant.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Getter
public class ProductDto {

    private final Integer id;
    private final String gtin;
    private final String name;
    private final BigDecimal priceGross;
    private final BigDecimal priceNet;
    private final String additionalInformation;

    public BigDecimal getPriceNet() {
        return priceNet.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getPriceGross() {
        return priceGross.setScale(2, RoundingMode.HALF_EVEN);
    }

}
