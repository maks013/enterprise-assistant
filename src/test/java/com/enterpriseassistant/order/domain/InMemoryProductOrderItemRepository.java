package com.enterpriseassistant.order.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryProductOrderItemRepository implements ProductOrderItemRepository {

    Map<Integer, ProductOrderItem> inMemoryProductOrderItems = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public ProductOrderItem save(ProductOrderItem productOrderItem) {
        id++;
        inMemoryProductOrderItems.put(id, productOrderItem);
        return inMemoryProductOrderItems.get(id);
    }
}
