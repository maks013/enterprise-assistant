package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.ServiceOrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
class ServiceOrderItem {

    private Integer id;
    private Integer orderId;
    private BigDecimal unitPriceGross;
    private BigDecimal unitPriceNet;
    private Integer quantity;
    private Integer serviceId;

    ServiceOrderItemDto toDto(){
        return ServiceOrderItemDto.builder()
                .id(id)
                .orderId(orderId)
                .unitPriceGross(unitPriceGross)
                .unitPriceNet(unitPriceNet)
                .quantity(quantity)
                .serviceId(serviceId)
                .build();
    }
}
