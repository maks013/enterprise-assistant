package com.enterpriseassistant.order.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddProductOrderItemDto {

    private Integer quantity;
    private Integer productId;

}
