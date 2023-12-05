package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.*;
import com.enterpriseassistant.product.domain.ProductFacade;
import com.enterpriseassistant.service.domain.ServiceFacade;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OrderMapper {

    private final ProductFacade productFacade;
    private final ServiceFacade serviceFacade;

    Order fromAddDto(AddOrderDto addOrderDto, int userId) {

        List<ProductOrderItem> productOrderItems = (addOrderDto.getProductOrderItems() != null) ?
                addOrderDto.getProductOrderItems().stream()
                        .map(this::fromAddProductOrderItemDto)
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        List<ServiceOrderItem> serviceOrderItems = (addOrderDto.getServiceOrderItems() != null) ?
                addOrderDto.getServiceOrderItems().stream()
                        .map(this::fromAddServiceOrderItemDto)
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        return Order.builder()
                .createdAt(LocalDateTime.now())
                .deadline(addOrderDto.getDeadline())
                .status(Order.Status.PROCESSING)
                .payment(addOrderDto.getPayment())
                .productOrderItems(productOrderItems)
                .serviceOrderItems(serviceOrderItems)
                .userId(userId)
                .clientId(addOrderDto.getClientId())
                .additionalInformation(addOrderDto.getAdditionalInformation())
                .build();
    }

    ProductOrderItem fromAddProductOrderItemDto(AddProductOrderItemDto addProductOrderItemDto){
        return ProductOrderItem.builder()
                .productId(addProductOrderItemDto.getProductId())
                .quantity(addProductOrderItemDto.getQuantity())
                .unitPriceGross(productFacade.getProductById(addProductOrderItemDto.getProductId()).getPriceGross())
                .unitPriceNet(productFacade.getProductById(addProductOrderItemDto.getProductId()).getPriceNet())
                .build();
    }

    ServiceOrderItem fromAddServiceOrderItemDto(AddServiceOrderItemDto addServiceOrderItemDto){
        return ServiceOrderItem.builder()
                .serviceId(addServiceOrderItemDto.getServiceId())
                .quantity(addServiceOrderItemDto.getQuantity())
                .unitPriceGross(serviceFacade.getServiceById(addServiceOrderItemDto.getServiceId()).getPriceGross())
                .unitPriceNet(serviceFacade.getServiceById(addServiceOrderItemDto.getServiceId()).getPriceNet())
                .build();
    }
}
