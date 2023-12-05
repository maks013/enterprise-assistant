package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.dto.ProductOrderItemDto;
import com.enterpriseassistant.order.dto.ServiceOrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
class Order {

    enum Status{
        PROCESSING, COMPLETED, CANCELLED
    }

    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Payment payment;
    private Status status;

    private List<ProductOrderItem> productOrderItems;
    private List<ServiceOrderItem> serviceOrderItems;

    private Integer userId;
    private Integer clientId;

    private String additionalInformation;


    OrderDto toDto(){
        List<ProductOrderItemDto> productOrderItemDtos = (productOrderItems != null) ?
                productOrderItems.stream().map(ProductOrderItem::toDto).collect(Collectors.toList()) :
                Collections.emptyList();

        List<ServiceOrderItemDto> serviceOrderItemDtos = (serviceOrderItems != null) ?
                serviceOrderItems.stream().map(ServiceOrderItem::toDto).collect(Collectors.toList()) :
                Collections.emptyList();

        return OrderDto.builder()
                .id(id)
                .createdAt(createdAt)
                .deadline(deadline)
                .payment(payment.toString())
                .status(status.toString())
                .productOrderItems(productOrderItemDtos)
                .serviceOrderItems(serviceOrderItemDtos)
                .userId(userId)
                .clientId(clientId)
                .additionalInformation(additionalInformation)
                .build();
    }
}
