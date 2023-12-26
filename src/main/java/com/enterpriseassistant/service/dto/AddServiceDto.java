package com.enterpriseassistant.service.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Getter
public class AddServiceDto {

    @NotBlank(message = "Service name can not be null")
    private final String name;
    @NotNull(message = "Service price can not be null")
    private final BigDecimal priceNet;
    private final String imageUrl;
    private final String additionalInformation;

}
