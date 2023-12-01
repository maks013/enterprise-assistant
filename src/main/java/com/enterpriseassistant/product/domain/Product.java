package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Getter
@Setter
class Product {

    private Integer id;
    private String gtin;
    private String name;
    private BigDecimal priceGross;
    private BigDecimal priceNet;
    private String imageUrl;
    private String additionalInformation;

    ProductDto toDto() {
        return ProductDto.builder()
                .id(id)
                .gtin(gtin)
                .name(name)
                .priceGross(getAsBigDecimal(priceGross))
                .priceNet(getAsBigDecimal(priceNet))
                .imageUrl(imageUrl)
                .additionalInformation(additionalInformation)
                .build();
    }

    private BigDecimal getAsBigDecimal(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

}
