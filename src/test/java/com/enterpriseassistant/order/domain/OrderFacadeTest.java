package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.AddOrderDto;
import com.enterpriseassistant.order.dto.AddProductOrderItemDto;
import com.enterpriseassistant.order.dto.AddServiceOrderItemDto;
import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.exception.InvalidStatusUpdate;
import com.enterpriseassistant.order.exception.OrderNotFound;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFacadeTest {

    OrderFacadeConfigForTests orderFacadeConfigForTests = new OrderFacadeConfigForTests();
    OrderFacade orderFacade = orderFacadeConfigForTests.orderFacade();

    @Test
    void should_find_all_orders() {
        //given
        final int size = orderFacade.findAllOrders().size();
        //when
        //then
        assertEquals(2, size);
    }

    @Test
    void should_find_all_orders_by_year() {
        //given
        final int size = orderFacade.findAllOrdersByYear(LocalDateTime.now().getYear()).size();
        //when
        //then
        assertEquals(2, size);
    }

    @Test
    void should_get_order_by_id() {
        //given
        final int orderId = 1;
        //when
        OrderDto orderDto = orderFacade.getOrderById(orderId);
        //then
        assertAll(
                () -> assertEquals(orderId, orderDto.getId()),
                () -> assertEquals(Order.Status.PROCESSING.toString(), orderDto.getStatus()),
                () -> assertEquals(1, orderDto.getClientId()),
                () -> assertEquals(1, orderDto.getClientId()),
                () -> assertEquals("Additional Information", orderDto.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_getting_order_by_not_existing_id() {
        //given
        final int notExistingId = 999;
        //when
        //then
        assertThrows(OrderNotFound.class, () -> orderFacade.getOrderById(notExistingId),
                "Order not found");
    }

    @Test
    void should_delete_order_successfully() {
        //given
        final int deletedOrderId = 1;
        //when
        final int sizeBeforeDelete = orderFacade.findAllOrders().size();
        orderFacade.deleteOrder(deletedOrderId);
        final int sizeAfterDelete = orderFacade.findAllOrders().size();
        //then
        assertAll(
                () -> assertEquals(1, sizeBeforeDelete - sizeAfterDelete),
                () -> assertThrows(OrderNotFound.class, () -> orderFacade.getOrderById(deletedOrderId))
        );
    }

    @Test
    void should_add_new_order_successfully() {
        //given
        final String username = "user2";
        AddProductOrderItemDto addProductOrderItemDto = AddProductOrderItemDto.builder()
                .quantity(3).productId(1).build();
        AddServiceOrderItemDto addServiceOrderItemDto = AddServiceOrderItemDto.builder()
                .quantity(5).serviceId(1).build();

        List<AddProductOrderItemDto> addProductOrderItemDtos = new ArrayList<>();
        List<AddServiceOrderItemDto> addServiceOrderItemDtos = new ArrayList<>();

        addProductOrderItemDtos.add(addProductOrderItemDto);
        addServiceOrderItemDtos.add(addServiceOrderItemDto);

        AddOrderDto addOrderDto = AddOrderDto.builder()
                .clientId(2)
                .productOrderItems(addProductOrderItemDtos)
                .serviceOrderItems(addServiceOrderItemDtos)
                .payment(Payment.TRANSFER)
                .daysToPay(DaysToPay.SEVEN)
                .deadline(LocalDateTime.now().plusDays(1))
                .additionalInformation("New Order")
                .build();
        //when
        final int sizeBeforeAdd = orderFacade.findAllOrders().size();
        OrderDto orderDto = orderFacade.addNewOrder(addOrderDto, username);
        final int sizeAfterAdd = orderFacade.findAllOrders().size();
        //then
        assertAll(
                () -> assertEquals(1, sizeAfterAdd - sizeBeforeAdd),
                () -> assertEquals(addOrderDto.getClientId(), orderDto.getClientId()),
                () -> assertEquals(addOrderDto.getDeadline(), orderDto.getDeadline()),
                () -> assertEquals("TRANSFER", orderDto.getPayment()),
                () -> assertEquals(addOrderDto.getAdditionalInformation(), orderDto.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_update_order_status_for_the_same() {
        //given
        final int orderId = 1;
        String status = "processing";
        //when
        //then
        assertThrows(InvalidStatusUpdate.class, () -> orderFacade.changeOrderStatus(orderId, status));
    }

    @Test
    void should_throw_exception_when_update_order_status_with_not_existing_status() {
        //given
        final int orderId = 1;
        String status = "not existing status";
        //when
        //then
        assertThrows(InvalidStatusUpdate.class, () -> orderFacade.changeOrderStatus(orderId, status));
    }

    @Test
    void should_update_order_status_successfully() {
        //given
        final int orderId = 1;
        String status = "completed";
        //when
        OrderDto orderDto = orderFacade.changeOrderStatus(orderId, status);
        //then
        assertEquals(Order.Status.COMPLETED.toString(), orderDto.getStatus());
    }

}
