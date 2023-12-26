package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.exception.OrderNotFound;
import com.enterpriseassistant.product.domain.InMemoryProductRepository;
import com.enterpriseassistant.product.domain.ProductFacade;
import com.enterpriseassistant.product.domain.ProductMapperForTests;
import com.enterpriseassistant.service.domain.InMemoryServiceRepository;
import com.enterpriseassistant.service.domain.ServiceFacade;
import com.enterpriseassistant.service.domain.ServiceMapperForTests;
import com.enterpriseassistant.user.domain.InMemoryUserRepository;
import com.enterpriseassistant.user.domain.UserDataValidatorForTests;
import com.enterpriseassistant.user.domain.UserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class OrderFacadeTest {

    private final InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();

    OrderFacade orderFacade = new OrderFacade(new UserFacade(inMemoryUserRepository, new UserDataValidatorForTests(inMemoryUserRepository), new BCryptPasswordEncoder()),
            new InMemoryOrderRepository(),
            new OrderMapper(new ProductFacade(new InMemoryProductRepository(), new ProductMapperForTests()), new ServiceFacade(new InMemoryServiceRepository(), new ServiceMapperForTests())));


    @Test
    void should_find_all_orders_and_return_size_1() {
        //given
        final int size = orderFacade.findAllOrders().size();
        //when
        //then
        assertEquals(1, size);
    }

    @Test
    void should_find_all_orders_by_year_return_size_1() {
        //given
        final int size = orderFacade.findAllOrdersByYear(2023).size();
        //when
        //then
        assertEquals(1, size);
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

//    @Test
//    void should_add_new_order_successfully() {
//        //given
//        final String username = "user2";
//        AddProductOrderItemDto addProductOrderItemDto = AddProductOrderItemDto.builder().quantity(3).productId(1).build();
//        AddServiceOrderItemDto addServiceOrderItemDto = AddServiceOrderItemDto.builder().quantity(5).serviceId(1).build();
//
//        List<AddProductOrderItemDto> addProductOrderItemDtos = new ArrayList<>();
//        List<AddServiceOrderItemDto> addServiceOrderItemDtos = new ArrayList<>();
//
//        addProductOrderItemDtos.add(addProductOrderItemDto);
//        addServiceOrderItemDtos.add(addServiceOrderItemDto);
//
//        AddOrderDto addOrderDto = AddOrderDto.builder()
//                .clientId(2)
//                .productOrderItems(addProductOrderItemDtos)
//                .serviceOrderItems(addServiceOrderItemDtos)
//                .payment(Payment.TRANSFER)
//                .deadline(LocalDateTime.now().plusDays(1))
//                .additionalInformation("New Order")
//                .build();
//        //when
//        final int sizeBeforeAdd = orderFacade.findAllOrders().size();
//        OrderDto orderDto = orderFacade.addNewOrder(addOrderDto, username);
//        final int sizeAfterAdd = orderFacade.findAllOrders().size();
//        //then
//        assertAll(
//                () -> assertEquals(1, sizeAfterAdd - sizeBeforeAdd),
//                () -> assertEquals(addOrderDto.getClientId(),orderDto.getClientId()),
//                () -> assertEquals(addOrderDto.getDeadline(),orderDto.getDeadline()),
//                () -> assertEquals("TRANSFER",orderDto.getPayment()),
//                () -> assertEquals(addOrderDto.getAdditionalInformation(),orderDto.getAdditionalInformation())
//        );
//    }

}
