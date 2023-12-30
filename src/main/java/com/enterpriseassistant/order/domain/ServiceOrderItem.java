package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.ServiceOrderItemDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Entity
@Table(name = "service_order_items")
@AllArgsConstructor
@NoArgsConstructor
class ServiceOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal unitPriceGross;
    private BigDecimal unitPriceNet;
    private Integer quantity;

    private String name;

    @Column(name = "service_id")
    private Integer serviceId;


    ServiceOrderItemDto toDto() {
        int orderId = order != null ? order.getId() : 0;
        return ServiceOrderItemDto.builder()
                .id(id)
                .orderId(orderId)
                .name(name)
                .unitPriceGross(unitPriceGross)
                .unitPriceNet(unitPriceNet)
                .quantity(quantity)
                .serviceId(serviceId)
                .build();
    }
}
