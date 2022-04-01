package br.com.fiap.abctechservice.application;

import br.com.fiap.abctechservice.application.dto.OrderDto;

import java.util.List;

public interface OrderApplication {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto closeOrder(OrderDto orderDto);

    List<OrderDto> listOrders();

    OrderDto getOrder(Long id);

}
