package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.dto.UpdateProductDto;
import com.enterpriseassistant.product.exception.InvalidNetPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
class ProductMapper {

    Product fromAddDto(AddProductDto addProductDto) {
        return Product.builder()
                .gtin(addProductDto.getGtin())
                .name(addProductDto.getName())
                .priceNet(addProductDto.getPriceNet())
                .priceGross(calculateGrossPrice(addProductDto.getPriceNet()))
                .imageUrl(addProductDto.getImageUrl())
                .additionalInformation(addProductDto.getAdditionalInformation())
                .build();
    }

    Product toUpdate(Product product, UpdateProductDto updateProductDto) {
        updateFieldIfNotNullOrEmpty(product.getGtin(),updateProductDto.getGtin(), value -> product.setGtin((String) value));
        updateFieldIfNotNullOrEmpty(product.getName(), updateProductDto.getName(), value -> product.setName((String) value));
        updateFieldIfNotNullOrEmpty(product.getPriceNet(), updateProductDto.getPriceNet(), priceNet -> {
            product.setPriceNet((BigDecimal) priceNet);
            product.setPriceGross(calculateGrossPrice((BigDecimal) priceNet));
        });
        updateFieldIfNotNullOrEmpty(product.getImageUrl(), updateProductDto.getImageUrl(), value -> product.setImageUrl((String) value));
        updateFieldIfNotNullOrEmpty(product.getAdditionalInformation(), updateProductDto.getAdditionalInformation(), value -> product.setAdditionalInformation((String) value));

        return product;
    }

    private void updateFieldIfNotNullOrEmpty(Object currentValue, Object updatedValue, Consumer<Object> setter) {
        if (updatedValue != null && !updatedValue.toString().isEmpty() && !currentValue.equals(updatedValue)) {
            setter.accept(updatedValue);
        }
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
