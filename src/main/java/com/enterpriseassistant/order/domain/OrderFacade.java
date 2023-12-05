package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.AddOrderDto;
import com.enterpriseassistant.order.dto.AddProductOrderItemDto;
import com.enterpriseassistant.order.dto.AddServiceOrderItemDto;
import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.exception.OrderNotFound;
import com.enterpriseassistant.user.domain.UserFacade;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderFacade {

    private final UserFacade userFacade;
    private final OrderRepository orderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final ServiceOrderItemRepository serviceOrderItemRepository;
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

    public OrderDto getOrderById(Integer id){
        return getOrder(id).toDto();
    }

    public void deleteOrder(Integer id) {
        final Order order = getOrder(id);
        orderRepository.delete(order);
    }

    public OrderDto addNewOrder(AddOrderDto addOrderDto, String username){
        final int userId = userFacade.getUserByUsername(username).getId();
        Order order = orderMapper.fromAddDto(addOrderDto, userId);

        saveProductOrderItems(addOrderDto.getProductOrderItems(), order.getId());
        saveServiceOrderItems(addOrderDto.getServiceOrderItems(), order.getId());

        return orderRepository.save(order).toDto();
    }

    private void saveProductOrderItems(List<AddProductOrderItemDto> productOrderItems, Integer orderId) {
        if (productOrderItems != null) {
            for (AddProductOrderItemDto addProductOrderItemDto : productOrderItems) {
                ProductOrderItem productOrderItem = orderMapper.fromAddProductOrderItemDto(addProductOrderItemDto);
                productOrderItem.setOrderId(orderId);
                productOrderItemRepository.save(productOrderItem);
            }
        }
    }

    private void saveServiceOrderItems(List<AddServiceOrderItemDto> serviceOrderItems, Integer orderId) {
        if (serviceOrderItems != null) {
            for (AddServiceOrderItemDto addServiceOrderItemDto : serviceOrderItems) {
                ServiceOrderItem serviceOrderItem = orderMapper.fromAddServiceOrderItemDto(addServiceOrderItemDto);
                serviceOrderItem.setOrderId(orderId);
                serviceOrderItemRepository.save(serviceOrderItem);
            }
        }
    }

    private Order getOrder(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFound::new);
    }
}
