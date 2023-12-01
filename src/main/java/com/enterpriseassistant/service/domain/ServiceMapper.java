package com.enterpriseassistant.service.domain;

import com.enterpriseassistant.product.exception.InvalidNetPrice;
import com.enterpriseassistant.service.dto.AddServiceDto;

import java.math.BigDecimal;

class ServiceMapper {

    Service fromAddDto(AddServiceDto addServiceDto) {
        return Service.builder()
                .name(addServiceDto.getName())
                .priceNet(addServiceDto.getPriceNet())
                .priceGross(calculateGrossPrice(addServiceDto.getPriceNet()))
                .imageUrl(addServiceDto.getImageUrl())
                .additionalInformation(addServiceDto.getAdditionalInformation())
                .build();
    }

    private BigDecimal calculateGrossPrice(BigDecimal priceNet) {
        BigDecimal priceGross;
        if (priceNet == null) {
            throw new InvalidNetPrice();
        }
        final BigDecimal netPercentage = BigDecimal.valueOf(1.23);
        priceGross = priceNet.multiply(netPercentage);
        return priceGross;
    }
}
