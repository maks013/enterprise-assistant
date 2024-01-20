package com.enterpriseassistant.order;

import com.enterpriseassistant.order.domain.OrderFacade;
import com.enterpriseassistant.order.dto.AddOrderDto;
import com.enterpriseassistant.order.dto.OrderDto;
import com.enterpriseassistant.order.dto.OrderStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@Api(value = "Order Controller", description = "Moduł Api odpowiadający za zamówienia")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    @ApiOperation(value = "Tworzy nowe zamówienie")
    public ResponseEntity<OrderDto> addNewOrder(@Valid @RequestBody AddOrderDto addOrderDto,
                                                Principal principal) {
        final OrderDto orderDto = orderFacade.addNewOrder(addOrderDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @GetMapping
    @ApiOperation(value = "Pobiera wszystkie zamówienia")
    public ResponseEntity<List<OrderDto>> findAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.findAllOrders());
    }

    @GetMapping("/year")
    @ApiOperation(value = "Pobiera zamówienia z danego roku")
    public ResponseEntity<List<OrderDto>> findOrder(@RequestBody Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.findAllOrdersByYear(year));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Pobiera zamówienie po identyfikatorze")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderFacade.getOrderById((id)));
    }

    @PatchMapping("/status/{id}")
    @ApiOperation(value = "Aktualizuje status zamówienia")
    public ResponseEntity<OrderDto> changeOrderStatus(@PathVariable Integer id,
                                                      @RequestBody OrderStatusDto orderStatusDto) {
        OrderDto orderDto = orderFacade.changeOrderStatus(id, orderStatusDto.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Usuwa zamówienie")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        orderFacade.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
