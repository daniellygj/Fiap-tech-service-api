package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.handler.exception.NotFoundException;
import br.com.fiap.abctechservice.handler.exception.OrderException;
import br.com.fiap.abctechservice.model.Order;
import br.com.fiap.abctechservice.model.Task;
import br.com.fiap.abctechservice.repository.OrderRepository;
import br.com.fiap.abctechservice.service.OrderService;
import br.com.fiap.abctechservice.service.TaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper mapper;

    private final OrderRepository orderRepository;

    private final TaskService taskService;

    public OrderServiceImpl(ModelMapper mapper, @Autowired OrderRepository orderRepository, @Autowired TaskService taskService) {
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.taskService = taskService;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // todo se nao tiver data de inicio, throw exception
        orderDto.setEndOrderLocation(null);
        Order order = mapper.map(orderDto, Order.class);

        int servicesQty = order.getTasks() != null ? order.getTasks().size() : 0;

        if (servicesQty < 1) {
            throw new OrderException.MinOrderTaskException();
        } else if (servicesQty > 15) {
            throw new OrderException.MaxOrderTaskException();
        }

        Order orderSaved = orderRepository.save(order);
        return mapper.map(orderSaved, OrderDto.class);
    }

    @Override
    public OrderDto closeOrder(OrderDto order) {
        // todo verificar se a ordem ja não foi fechada. Se ja foi, extourar exception
        // todo estourar erro se a ordem não existir (parecido com o createOrder)
        Order orderFound = orderRepository.getById(order.getId());
        order.setEndOrderLocation(order.getEndOrderLocation());
        Order orderSaved = orderRepository.save(orderFound);
        return mapper.map(orderSaved, OrderDto.class);
    }

    @Override
    public List<OrderDto> listOrders() {
        List<Order> orders = orderRepository.findAll();
        Type listType = new TypeToken<List<OrderDto>>(){}.getType();
        return mapper.map(orders, listType);
    }

    @Override
    public OrderDto getOrder(Long id) {
        Order orderFound = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
        return mapper.map(orderFound, OrderDto.class);
    }
}
