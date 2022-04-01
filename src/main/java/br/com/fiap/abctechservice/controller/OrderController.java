package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.application.OrderApplication;
import br.com.fiap.abctechservice.application.dto.OrderDto;
import br.com.fiap.abctechservice.model.Order;
import br.com.fiap.abctechservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderApplication orderApplication;

    public OrderController(@Autowired OrderApplication orderApplication) {
        this.orderApplication = orderApplication;
    }

    @PostMapping
    public void createOrder(@RequestBody @Valid OrderDto orderDto) {
        orderApplication.createOrder(orderDto);
//        return ResponseEntity.ok(orderApplication.createOrder(orderDto)); todo
    }

    @PutMapping
    public ResponseEntity<OrderDto> closeOrder(@RequestBody @Valid OrderDto order) {
        return ResponseEntity.ok(orderApplication.closeOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderApplication.listOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderApplication.getOrder(id));
    }
}
