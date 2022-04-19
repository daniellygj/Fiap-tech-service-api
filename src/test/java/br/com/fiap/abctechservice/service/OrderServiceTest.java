package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.TaskDto;
import br.com.fiap.abctechservice.handler.exception.NotFoundException;
import br.com.fiap.abctechservice.handler.exception.OrderException;
import br.com.fiap.abctechservice.model.*;
import br.com.fiap.abctechservice.repository.OrderRepository;
import br.com.fiap.abctechservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TaskService serviceService;

    @Mock
    private ModelMapper modelMapper;

    private OrderService orderService;

    private final ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(modelMapper, orderRepository, serviceService);
    }

    @Test
    public void createOrder_ShouldSucceed() {
        Task task = Task
                .builder()
                .build();

        TaskDto taskFound = mapper.map(task, TaskDto.class);

        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .tasks(Collections.singletonList(task))
                .startOrderLocation(startOrderLocation)
                .build();

        OrderDto orderDto = mapper.map(order, OrderDto.class);

        when(serviceService.getTaskById(task.getId())).thenReturn(taskFound);
        when(orderRepository.save(order)).thenReturn(order);
        when(modelMapper.map(orderDto, Order.class)).thenReturn(order);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);

        OrderDto orderSavedDto = orderService.createOrder(orderDto);

        assertEquals(orderSavedDto.getTasks().get(0).getId(), taskFound.getId());
        assertEquals(orderSavedDto.getTasks().get(0).getName(), taskFound.getName());
        assertEquals(orderSavedDto.getTasks().get(0).getDescription(), taskFound.getDescription());
        assertNotNull(orderSavedDto.getStartOrderLocation());
        assertNull(orderSavedDto.getEndOrderLocation());
        assertEquals(orderSavedDto.getOperatorId(), order.getOperatorId());
    }

    @Test
    public void createOrderWithNoService_ShouldFail() {
        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .startOrderLocation(startOrderLocation)
                .build();

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        when(modelMapper.map(orderDto, Order.class)).thenReturn(order);

        assertThrows(
                OrderException.MinOrderTaskException.class,
                () -> orderService.createOrder(orderDto)
        );
    }

    @Test
    public void createOrderWithMaxService_ShouldFail() {
        List<Task> tasks = generateMockTask(16);

        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .startOrderLocation(startOrderLocation)
                .tasks(tasks)
                .build();

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        when(modelMapper.map(orderDto, Order.class)).thenReturn(order);

        assertThrows(
                OrderException.MaxOrderTaskException.class,
                () -> orderService.createOrder(orderDto)
        );
    }

    @Test
    public void closeOrder_shouldSucceed() {
        Task task = TaskTestBuilder
                .init()
                .withDefaultValues()
                .build();

        OrderLocation orderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();


        Order orderSaved = OrderTestBuilder
                .init()
                .withDefaultValues()
                .tasks(Collections.singletonList(task))
                .startOrderLocation(orderLocation)
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .tasks(Collections.singletonList(task))
                .startOrderLocation(orderLocation)
                .endOrderLocation(orderLocation)
                .build();

        OrderDto orderDto = mapper.map(order, OrderDto.class);

        when(orderRepository.getById(order.getId())).thenReturn(orderSaved);
        when(orderRepository.save(orderSaved)).thenReturn(orderSaved);
        when(modelMapper.map(orderSaved, OrderDto.class)).thenReturn(orderDto);

        OrderDto orderReturned = orderService.closeOrder(orderDto);

        assertEquals(orderReturned.getStartOrderLocation(), orderDto.getStartOrderLocation());
        assertEquals(orderReturned.getEndOrderLocation(), orderDto.getEndOrderLocation());
        assertEquals(orderReturned.getTasks(), orderDto.getTasks());
        assertEquals(orderReturned.getOperatorId(), order.getOperatorId());
    }

    @Test
    public void listOrderWithOrdersSaved_shouldSucceed() {
        Order order1 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(1L)
                .build();

        Order order2 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        Order order3 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(3L)
                .build();

        Type listType = new TypeToken<List<OrderDto>>(){}.getType();
        List<Order> orders = List.of(order1, order2, order3);
        List<OrderDto> ordersDto = mapper.map(orders, listType);

        when(orderRepository.findAll()).thenReturn(orders);
        when(modelMapper.map(orders, listType)).thenReturn(ordersDto);

        List<OrderDto> orderList = orderService.listOrders();

        assertEquals(orderList.size(), 3);
        assertTrue(orderList.contains(ordersDto.get(0)));
        assertTrue(orderList.contains(ordersDto.get(1)));
        assertTrue(orderList.contains(ordersDto.get(2)));
    }

    @Test
    public void listOrderWithNoOrdersSaved_shouldSucceed() {

        Type listType = new TypeToken<List<OrderDto>>(){}.getType();
        List<Order> orders = List.of();

        when(orderRepository.findAll()).thenReturn(List.of());
        when(modelMapper.map(orders, listType)).thenReturn(List.of());

        List<OrderDto> orderList = orderService.listOrders();

        assertEquals(orderList.size(), 0);
    }

    @Test
    public void getOrder_shouldSucceed() {
        Order order =  OrderTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Optional<Order> orderOptional = Optional.ofNullable(order);
        OrderDto orderDto = mapper.map(order, OrderDto.class);

        when(orderRepository.findById(order.getId())).thenReturn(orderOptional);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);

        OrderDto orderFound = orderService.getOrder(order.getId());

        assertEquals(orderFound.getOperatorId(), order.getOperatorId());
    }

    @Test
    public void getOrder_shouldFail() {

        when(orderRepository.findById(1L)).thenThrow(NotFoundException.class);;

        assertThrows(
                NotFoundException.class,
                () -> orderService.getOrder(1L)
        );
    }

    // todo - corrigir pra criar objeto
    private List<Task> generateMockTask(int number) {
        ArrayList<Task> list = new ArrayList<>();

        for (int j = 0; j < number; j++) {
            Task task = TaskTestBuilder
                    .init()
                    .withDefaultValues()
                    .id((long) j)
                    .build();

            list.add(task);
        }

        return list;
    }
}
