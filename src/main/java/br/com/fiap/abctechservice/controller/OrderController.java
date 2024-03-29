package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.OrderDtoCreate;
import br.com.fiap.abctechservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDtoCreate orderDto) {
        return ResponseEntity.ok(service.createOrder(orderDto));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(service.listOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }
}
