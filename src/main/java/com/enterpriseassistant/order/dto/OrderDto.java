package com.enterpriseassistant.order.dto;

import com.enterpriseassistant.order.domain.DaysToPay;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrderDto {

    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private String payment;
    private DaysToPay daysToPay;
    private String status;

    private List<ProductOrderItemDto> productOrderItems;
    private List<ServiceOrderItemDto> serviceOrderItems;

    private Integer userId;
    private Integer clientId;

    private String additionalInformation;
}
