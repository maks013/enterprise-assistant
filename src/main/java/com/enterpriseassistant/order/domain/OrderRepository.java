package com.enterpriseassistant.order.domain;

import java.util.List;
import java.util.Optional;

interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Integer id);

    Optional<List<Order>> findByYear(Integer year);

    List<Order> findAll();

    void delete(Order order);
}
