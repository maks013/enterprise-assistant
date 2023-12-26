package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.dto.ProductOrderItemDto;
import com.enterpriseassistant.order.dto.ServiceOrderItemDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
class Order {

    enum Status {
        PROCESSING, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdAt;
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<ProductOrderItem> productOrderItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<ServiceOrderItem> serviceOrderItems;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "client_id")
    private Integer clientId;

    private String additionalInformation;

    boolean isValidOrder() {
        return (productOrderItems != null && !productOrderItems.isEmpty())
                || (serviceOrderItems != null && !serviceOrderItems.isEmpty());
    }

    void addProductOrderItem(ProductOrderItem item) {
        if (this.productOrderItems == null) {
            this.productOrderItems = new ArrayList<>();
        }
        this.productOrderItems.add(item);
        item.setOrder(this);
    }

    void addServiceOrderItem(ServiceOrderItem item) {
        if (this.serviceOrderItems == null) {
            this.serviceOrderItems = new ArrayList<>();
        }
        this.serviceOrderItems.add(item);
        item.setOrder(this);
    }

    OrderDto toDto() {
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
