package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.exception.InvalidNetPrice;

import java.math.BigDecimal;

class ProductMapper {

    Product fromAddDto(AddProductDto addProductDto) {
        return Product.builder()
                .gtin(addProductDto.getGtin())
                .name(addProductDto.getName())
                .priceNet(addProductDto.getPriceNet())
                .priceGross(calculateGrossPrice(addProductDto.getPriceNet()))
                .additionalInformation(addProductDto.getAdditionalInformation())
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
