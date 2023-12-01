package com.enterpriseassistant.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ServiceDto {

    private final Integer id;
    private final String name;
    private final BigDecimal priceGross;
    private final BigDecimal priceNet;
    private final String imageUrl;
    private final String additionalInformation;

}
