package com.enterpriseassistant.order.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddServiceOrderItemDto {

    private Integer quantity;
    private Integer serviceId;

}
