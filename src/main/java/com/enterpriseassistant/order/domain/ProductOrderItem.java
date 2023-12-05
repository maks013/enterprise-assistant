package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.ProductOrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
class ProductOrderItem {

    private Integer id;
    private Integer orderId;
    private BigDecimal unitPriceGross;
    private BigDecimal unitPriceNet;
    private Integer quantity;
    private Integer productId;

    ProductOrderItemDto toDto(){
        return ProductOrderItemDto.builder()
                .id(id)
                .orderId(orderId)
                .unitPriceGross(unitPriceGross)
                .unitPriceNet(unitPriceNet)
                .quantity(quantity)
                .productId(productId)
                .build();
    }
}
