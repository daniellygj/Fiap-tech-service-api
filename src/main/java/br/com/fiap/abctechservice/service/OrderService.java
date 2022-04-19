package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.OrderDtoCreate;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDtoCreate order);

    OrderDto closeOrder(OrderDto order);

    List<OrderDto> listOrders();

    OrderDto getOrder(Long id) ;
}
