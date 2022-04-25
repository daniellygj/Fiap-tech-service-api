package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.OrderDtoCreate;
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
public class OrdersServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private ModelMapper modelMapper;

    private OrderService orderService;

    private final ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(modelMapper, orderRepository, taskService);
    }

    @Test
    public void createOrder_ShouldSucceed() {
        Task task = TaskTestBuilder
                .init()
                .withDefaultValues()
                .build();

        TaskDto taskFound = mapper.map(task, TaskDto.class);

        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Orders orders = OrderTestBuilder
                .init()
                .withDefaultValues()
                .tasks(Collections.singletonList(task))
                .startOrderLocation(startOrderLocation)
                .build();

        List<Long> tasksId = List.of(task.getId());

        OrderDto orderDto = mapper.map(orders, OrderDto.class);
        OrderDtoCreate orderDtoCreate = OrderDtoCreate
                .builder()
                .id(orderDto.getId())
                .endOrderLocation(orderDto.getEndOrderLocation())
                .startOrderLocation(orderDto.getStartOrderLocation())
                .tasks(tasksId)
                .build();

        when(taskService.getTaskById(taskFound.getId())).thenReturn(taskFound);
        when(orderRepository.save(orders)).thenReturn(orders);
        when(modelMapper.map(orderDtoCreate, Orders.class)).thenReturn(orders);
        when(modelMapper.map(orders, OrderDto.class)).thenReturn(orderDto);

        OrderDto orderSavedDto = orderService.createOrder(orderDtoCreate);

        assertEquals(orderSavedDto.getTasks().get(0).getId(), taskFound.getId());
        assertEquals(orderSavedDto.getTasks().get(0).getName(), taskFound.getName());
        assertEquals(orderSavedDto.getTasks().get(0).getDescription(), taskFound.getDescription());
        assertNotNull(orderSavedDto.getStartOrderLocation());
        assertNull(orderSavedDto.getEndOrderLocation());
        assertEquals(orderSavedDto.getOperatorId(), orders.getOperatorId());
    }

    @Test
    public void createOrderWithEmptyStartDate_ShouldFail() {
        Task task = TaskTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Orders orders = OrderTestBuilder
                .init()
                .withDefaultValues()
                .tasks(Collections.singletonList(task))
                .build();

        List<Long> tasksId = List.of(task.getId());

        OrderDto orderDto = mapper.map(orders, OrderDto.class);
        OrderDtoCreate orderDtoCreate = OrderDtoCreate
                .builder()
                .id(orderDto.getId())
                .endOrderLocation(orderDto.getEndOrderLocation())
                .startOrderLocation(orderDto.getStartOrderLocation())
                .tasks(tasksId)
                .build();


        assertThrows(
                OrderException.OrderStartDateNullException.class,
                () -> orderService.createOrder(orderDtoCreate)
        );

    }

    @Test
    public void createOrderWithNoService_ShouldFail() {
        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Orders orders = OrderTestBuilder
                .init()
                .withDefaultValues()
                .startOrderLocation(startOrderLocation)
                .build();

        OrderDto orderDto = mapper.map(orders, OrderDto.class);
        OrderDtoCreate orderDtoCreate = OrderDtoCreate
                .builder()
                .id(orderDto.getId())
                .endOrderLocation(orderDto.getEndOrderLocation())
                .startOrderLocation(orderDto.getStartOrderLocation())
                .tasks(List.of())
                .build();

        when(modelMapper.map(orderDto, Orders.class)).thenReturn(orders);

        assertThrows(
                OrderException.MinOrderTaskException.class,
                () -> orderService.createOrder(orderDtoCreate)
        );
    }

    @Test
    public void createOrderWithMaxService_ShouldFail() {
        List<Task> tasks = generateMockTask(16);
        List<Long> tasksId = generateMockTaskId(16);

        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Orders orders = OrderTestBuilder
                .init()
                .withDefaultValues()
                .startOrderLocation(startOrderLocation)
                .tasks(tasks)
                .build();

        OrderDto orderDto = mapper.map(orders, OrderDto.class);
        OrderDtoCreate orderDtoCreate = OrderDtoCreate
                .builder()
                .id(orderDto.getId())
                .endOrderLocation(orderDto.getEndOrderLocation())
                .startOrderLocation(orderDto.getStartOrderLocation())
                .tasks(tasksId)
                .build();

        when(modelMapper.map(orderDto, Orders.class)).thenReturn(orders);

        assertThrows(
                OrderException.MaxOrderTaskException.class,
                () -> orderService.createOrder(orderDtoCreate)
        );
    }

    @Test
    public void listOrderWithOrdersSaved_shouldSucceed() {
        Orders orders1 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(1L)
                .build();

        Orders orders2 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        Orders orders3 = OrderTestBuilder
                .init()
                .withDefaultValues()
                .id(3L)
                .build();

        Type listType = new TypeToken<List<OrderDto>>(){}.getType();
        List<Orders> orders = List.of(orders1, orders2, orders3);
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
        List<Orders> orders = List.of();

        when(orderRepository.findAll()).thenReturn(List.of());
        when(modelMapper.map(orders, listType)).thenReturn(List.of());

        List<OrderDto> orderList = orderService.listOrders();

        assertEquals(orderList.size(), 0);
    }

    @Test
    public void getOrder_shouldSucceed() {
        Orders orders =  OrderTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Optional<Orders> orderOptional = Optional.ofNullable(orders);
        OrderDto orderDto = mapper.map(orders, OrderDto.class);

        when(orderRepository.findById(orders.getId())).thenReturn(orderOptional);
        when(modelMapper.map(orders, OrderDto.class)).thenReturn(orderDto);

        OrderDto orderFound = orderService.getOrder(orders.getId());

        assertEquals(orderFound.getOperatorId(), orders.getOperatorId());
    }

    @Test
    public void getOrder_shouldFail() {

        when(orderRepository.findById(1L)).thenThrow(NotFoundException.class);;

        assertThrows(
                NotFoundException.class,
                () -> orderService.getOrder(1L)
        );
    }

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

    private List<Long> generateMockTaskId(int number) {
        ArrayList<Long> list = new ArrayList<>();

        for (int j = 0; j < number; j++) {
            list.add((long) j);
        }

        return list;
    }
}
