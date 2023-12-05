package com.enterpriseassistant.order.dto;

import com.enterpriseassistant.order.domain.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class AddOrderDto {

    private Integer clientId;
    private List<AddProductOrderItemDto> productOrderItems;
    private List<AddServiceOrderItemDto> serviceOrderItems;
    private LocalDateTime deadline;
    private Payment payment;
    private String additionalInformation;
}
