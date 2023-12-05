package com.enterpriseassistant.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryOrderRepository implements OrderRepository {

    Map<Integer, Order> inMemoryOrders = new ConcurrentHashMap<>();

    ProductOrderItem productOrderItem1 = new ProductOrderItem(1, 1, new BigDecimal("10.00"), new BigDecimal("12.30"), 2, 101);
    ProductOrderItem productOrderItem2 = new ProductOrderItem(2, 1, new BigDecimal("10.00"), new BigDecimal("12.30"), 1, 102);

    ServiceOrderItem serviceOrderItem1 = new ServiceOrderItem(1, 1, new BigDecimal("20.00"), new BigDecimal("24.60"), 3, 201);
    ServiceOrderItem serviceOrderItem2 = new ServiceOrderItem(2, 1, new BigDecimal("20.00"), new BigDecimal("24.60"), 1, 202);

    public InMemoryOrderRepository() {
        List<ProductOrderItem> listOfProductItem1 = new ArrayList<>();
        listOfProductItem1.add(productOrderItem1);
        listOfProductItem1.add(productOrderItem2);

        List<ServiceOrderItem> listOfServiceItem1 = new ArrayList<>();
        listOfServiceItem1.add(serviceOrderItem1);
        listOfServiceItem1.add(serviceOrderItem2);

        final Order order1 = new Order(1, LocalDateTime.now(), LocalDateTime.now().plusDays(15),Payment.CASH, Order.Status.PROCESSING,
                listOfProductItem1, listOfServiceItem1, 1, 1, "Additional Information");

        inMemoryOrders.put(1, order1);
    }

    @Override
    public Order save(Order order) {
        final int newOrderId = 2;
        order.setId(newOrderId);
        inMemoryOrders.put(newOrderId,order);
        return inMemoryOrders.get(newOrderId);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.ofNullable(inMemoryOrders.get(id));
    }

    @Override
    public Optional<List<Order>> findByYear(Integer year) {
        List<Order> ordersOfYear = inMemoryOrders.values()
                .stream()
                .filter(order -> order.getCreatedAt().getYear() == year)
                .collect(Collectors.toList());

        return ordersOfYear.isEmpty() ? Optional.empty() : Optional.of(ordersOfYear);
    }

    @Override
    public List<Order> findAll() {
        return inMemoryOrders.values().stream().toList();
    }

    @Override
    public void delete(Order order) {
        inMemoryOrders.remove(order.getId());
    }
}
