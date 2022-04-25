package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.OrderDtoCreate;
import br.com.fiap.abctechservice.dto.TaskDto;
import br.com.fiap.abctechservice.handler.exception.NotFoundException;
import br.com.fiap.abctechservice.handler.exception.OrderException;
import br.com.fiap.abctechservice.model.OrderLocation;
import br.com.fiap.abctechservice.model.Orders;
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
    public OrderDto createOrder(OrderDtoCreate orderDto) {

        if (orderDto.getStartOrderLocation() == null || orderDto.getStartOrderLocation().getDate() == null) {
            throw new OrderException.OrderStartDateNullException();
        }

        int servicesQty = orderDto.getTasks() != null ? orderDto.getTasks().size() : 0;

        if (servicesQty < 1) {
            throw new OrderException.MinOrderTaskException();
        } else if (servicesQty > 15) {
            throw new OrderException.MaxOrderTaskException();
        }

        List<Task> taskList = new ArrayList<>();

        orderDto.getTasks().forEach(taskId -> {
            TaskDto taskDto = taskService.getTaskById(taskId);
            Task task = mapper.map(taskDto, Task.class);
            if (!taskList.contains(task)) {
                taskList.add(task);
            }
        });

        Orders orders = mapper.map(orderDto, Orders.class);
        orders.setTasks(taskList);

        Orders ordersSaved = orderRepository.save(orders);
        return mapper.map(ordersSaved, OrderDto.class);
    }


    @Override
    public List<OrderDto> listOrders() {
        List<Orders> orders = orderRepository.findAll();
        Type listType = new TypeToken<List<OrderDto>>(){}.getType();
        return mapper.map(orders, listType);
    }

    @Override
    public OrderDto getOrder(Long id) {
        Orders ordersFound = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
        return mapper.map(ordersFound, OrderDto.class);
    }
}
