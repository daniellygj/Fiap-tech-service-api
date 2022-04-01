package br.com.fiap.abctechservice.application.impl;

import br.com.fiap.abctechservice.application.OrderApplication;
import br.com.fiap.abctechservice.application.dto.OrderDto;
import br.com.fiap.abctechservice.application.dto.OrderLocationDto;
import br.com.fiap.abctechservice.model.Order;
import br.com.fiap.abctechservice.model.OrderLocation;
import br.com.fiap.abctechservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderApplicationImpl implements OrderApplication {

    private final OrderService orderService;

    private ModelMapper mapper = new ModelMapper();

    public OrderApplicationImpl(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .operatorId(orderDto.getOperatorId())
                .startOrderLocation(getOrderLocationFromUnderLocationDto(orderDto.getStartOrderLocation()))
                .endOrderLocation(getOrderLocationFromUnderLocationDto(orderDto.getEndOrderLocation()))
                .build();

        List<Long> id = new ArrayList<>();

        return orderService.createOrder(order, id);
    }

    @Override
    public OrderDto closeOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public List<OrderDto> listOrders() {
        return null;
    }

    @Override
    public OrderDto getOrder(Long id) {
        Order order = orderService.getOrder(id);
        return mapper.map(order, OrderDto.class);
    }

    private OrderLocation getOrderLocationFromUnderLocationDto(OrderLocationDto orderLocationDto) {
        return OrderLocation.builder()
                .latitude(orderLocationDto.getLatitude())
                .longitude(orderLocationDto.getLongitude())
                .id(orderLocationDto.getId())
                .build();
    }
}
