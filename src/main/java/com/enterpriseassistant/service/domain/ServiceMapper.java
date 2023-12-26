package com.enterpriseassistant.service.domain;

import com.enterpriseassistant.product.exception.InvalidNetPrice;
import com.enterpriseassistant.service.dto.AddServiceDto;
import com.enterpriseassistant.service.dto.UpdateServiceDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
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

    Service toUpdate(Service service, UpdateServiceDto updateServiceDto) {
        updateFieldIfNotNullOrEmpty(service.getName(), updateServiceDto.getName(), value -> service.setName((String) value));
        updateFieldIfNotNullOrEmpty(service.getPriceNet(), updateServiceDto.getPriceNet(), priceNet -> {
            service.setPriceNet((BigDecimal) priceNet);
            service.setPriceGross(calculateGrossPrice((BigDecimal) priceNet));
        });
        updateFieldIfNotNullOrEmpty(service.getImageUrl(), updateServiceDto.getImageUrl(), value -> service.setImageUrl((String) value));
        updateFieldIfNotNullOrEmpty(service.getAdditionalInformation(), updateServiceDto.getAdditionalInformation(), value -> service.setAdditionalInformation((String) value));

        return service;
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
