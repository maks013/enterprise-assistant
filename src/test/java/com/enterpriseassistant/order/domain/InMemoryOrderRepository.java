package com.enterpriseassistant.order.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryOrderRepository implements OrderRepository {

    Map<Integer, Order> inMemoryOrders = new ConcurrentHashMap<>();

    List<ProductOrderItem> listOfProductItem1 = new ArrayList<>();
    List<ServiceOrderItem> listOfServiceItem1 = new ArrayList<>();

    Order order1 = new Order(1, LocalDateTime.now(), LocalDateTime.now().plusDays(15), Payment.CASH, DaysToPay.SEVEN, Order.Status.PROCESSING,
            listOfProductItem1, listOfServiceItem1, 1, 1, "Additional Information");

    Order order2 = new Order(2, LocalDateTime.now(), LocalDateTime.now().plusDays(15), Payment.CASH, DaysToPay.SEVEN, Order.Status.PROCESSING,
            listOfProductItem1, null, 1, 1, "Additional Information");

    ProductOrderItem productOrderItem1 = new ProductOrderItem(1, order1, new BigDecimal("10.00"), new BigDecimal("12.30"), 2, "Product Example", 101);
    ProductOrderItem productOrderItem2 = new ProductOrderItem(2, order1, new BigDecimal("10.00"), new BigDecimal("12.30"), 1, "Product Example 2", 102);

    ServiceOrderItem serviceOrderItem1 = new ServiceOrderItem(1, order1, new BigDecimal("20.00"), new BigDecimal("24.60"), 3, "Service Example", 201);
    ServiceOrderItem serviceOrderItem2 = new ServiceOrderItem(2, order1, new BigDecimal("20.00"), new BigDecimal("24.60"), 1, "Service Example 2", 202);

    public InMemoryOrderRepository() {
        listOfProductItem1.add(productOrderItem1);
        listOfProductItem1.add(productOrderItem2);

        listOfServiceItem1.add(serviceOrderItem1);
        listOfServiceItem1.add(serviceOrderItem2);

        inMemoryOrders.put(1, order1);
        inMemoryOrders.put(2, order2);
    }

    @Override
    public Order save(Order order) {
        final int newOrderId = 3;
        order.setId(newOrderId);
        inMemoryOrders.put(newOrderId, order);
        return inMemoryOrders.get(newOrderId);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.ofNullable(inMemoryOrders.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return inMemoryOrders.values().stream().toList();
    }

    @Override
    public List<Order> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Order> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public <S extends Order> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Order> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Order> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Order getOne(Integer integer) {
        return null;
    }

    @Override
    public Order getById(Integer integer) {
        return null;
    }

    @Override
    public Order getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Order> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Order> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Order> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Order> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public void delete(Order order) {
        inMemoryOrders.remove(order.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Order> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
