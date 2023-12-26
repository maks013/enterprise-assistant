package com.enterpriseassistant.product.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Getter
public class AddProductDto {

    @NotBlank(message = "Gtin number can not be null")
    private final String gtin;
    @NotBlank(message = "Product name can not be null")
    private final String name;
    @NotNull(message = "Product price can not be null")
    private final BigDecimal priceNet;
    private final String imageUrl;
    private final String additionalInformation;

}
