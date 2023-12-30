package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.AddOrderDto;
import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.exception.InvalidOrderCreation;
import com.enterpriseassistant.order.exception.InvalidStatusUpdate;
import com.enterpriseassistant.order.exception.OrderNotFound;
import com.enterpriseassistant.user.domain.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderFacade {

    private final UserFacade userFacade;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findAllOrdersByYear(int year) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getCreatedAt().getYear() == year)
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Integer id) {
        return getOrder(id).toDto();
    }

    @Transactional
    public OrderDto changeOrderStatus(Integer id, String newStatus) {
        Order order = getOrder(id);
        Order.Status status = validateAndConvertStatus(newStatus);

        if (order.getStatus() == status) {
            throw new InvalidStatusUpdate();
        }

        order.setStatus(status);

        return orderRepository.save(order).toDto();
    }

    public void deleteOrder(Integer id) {
        final Order order = getOrder(id);
        orderRepository.delete(order);
    }

    @Transactional
    public OrderDto addNewOrder(AddOrderDto addOrderDto, String username) {
        final int userId = userFacade.getUserByUsername(username).getId();
        Order order = orderMapper.fromAddDto(addOrderDto, userId);

        if (!order.isValidOrder()) {
            throw new InvalidOrderCreation();
        }

        Order savedOrder = orderRepository.save(order);

        return savedOrder.toDto();
    }

    private Order getOrder(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFound::new);
    }

    private Order.Status validateAndConvertStatus(String statusStr) {
        try {
            return Order.Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusUpdate();
        }
    }
}
