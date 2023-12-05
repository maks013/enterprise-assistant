package com.enterpriseassistant.order.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryServiceOrderItemRepository implements ServiceOrderItemRepository{

    Map<Integer, ServiceOrderItem> inMemoryServiceOrderItems = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public ServiceOrderItem save(ServiceOrderItem serviceOrderItem) {
        id++;
        inMemoryServiceOrderItems.put(id, serviceOrderItem);
        return inMemoryServiceOrderItems.get(id);
    }
}
