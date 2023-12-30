package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.ProductOrderItemDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
class ProductOrderItem {

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

    @Column(name = "product_id")
    private Integer productId;

    ProductOrderItemDto toDto(){
        int orderId = order != null ? order.getId() : 0;
        return ProductOrderItemDto.builder()
                .id(id)
                .orderId(orderId)
                .name(name)
                .unitPriceGross(unitPriceGross)
                .unitPriceNet(unitPriceNet)
                .quantity(quantity)
                .productId(productId)
                .build();
    }
}
