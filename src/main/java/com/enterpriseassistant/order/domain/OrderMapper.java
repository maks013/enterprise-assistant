package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.*;
import com.enterpriseassistant.product.domain.ProductFacade;
import com.enterpriseassistant.service.domain.ServiceFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class OrderMapper {

    private final ProductFacade productFacade;
    private final ServiceFacade serviceFacade;

    Order fromAddDto(AddOrderDto addOrderDto, int userId) {
        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .deadline(addOrderDto.getDeadline())
                .status(Order.Status.PROCESSING)
                .payment(addOrderDto.getPayment())
                .userId(userId)
                .clientId(addOrderDto.getClientId())
                .additionalInformation(addOrderDto.getAdditionalInformation())
                .build();

        if (addOrderDto.getProductOrderItems() != null) {
            addOrderDto.getProductOrderItems().forEach(productOrderItemDto -> {
                ProductOrderItem productOrderItem = fromAddProductOrderItemDto(productOrderItemDto);
                order.addProductOrderItem(productOrderItem);
            });
        }

        if (addOrderDto.getServiceOrderItems() != null) {
            addOrderDto.getServiceOrderItems().forEach(serviceOrderItemDto -> {
                ServiceOrderItem serviceOrderItem = fromAddServiceOrderItemDto(serviceOrderItemDto);
                order.addServiceOrderItem(serviceOrderItem);
            });
        }

        return order;
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
