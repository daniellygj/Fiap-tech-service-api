package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto order);

    OrderDto closeOrder(OrderDto order);

    List<OrderDto> listOrders();

    OrderDto getOrder(Long id) ;
}
