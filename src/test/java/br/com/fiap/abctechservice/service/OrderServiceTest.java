package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.application.dto.OrderDto;
import br.com.fiap.abctechservice.model.*;
import br.com.fiap.abctechservice.repository.OrderRepository;
import br.com.fiap.abctechservice.service.impl.OrderServiceImpl;
import br.com.fiap.abctechservice.handler.exception.GenericException;
import br.com.fiap.abctechservice.handler.exception.OrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

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
    private AssistanceService assistanceService;

    private OrderService orderService;

    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository, assistanceService);
    }

    @Test
    public void createOrder_ShouldSucceed() {
        Assistance assistanceFound = AssistanceTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Assistance assistance = Assistance
                .builder()
                .id(assistanceFound.getId())
                .build();

        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .services(Collections.singletonList(assistance))
                .startOrderLocation(startOrderLocation)
                .build();

        when(assistanceService.getAssist(assistance.getId())).thenReturn(assistanceFound);
        when(orderRepository.save(order)).thenReturn(order);

        List<Long> idList =  generateMockAssistance(3);

        OrderDto orderSavedDto = orderService.createOrder(order, idList);
        Order orderSaved = mapper.map(orderSavedDto, Order.class);

        assertEquals(orderSaved.getServices(), Collections.singletonList(assistanceFound));
//        assertEquals(orderSaved.getStartOrderLocation(), startOrderLocation); todo
        assertNull(orderSaved.getEndOrderLocation());
        assertEquals(orderSaved.getOperatorId(), order.getOperatorId());
    }

    @Test
    public void createOrderWithNoAssists_ShouldFail() {
        OrderLocation startOrderLocation = OrderLocationTestbuilder
                .init()
                .withDefaultValues()
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .startOrderLocation(startOrderLocation)
                .build();

        assertThrows(
                OrderException.MinOrderAssistsException.class,
                () -> orderService.createOrder(order, List.of())
        );
    }

//    @Test
//    public void createOrderWithMaxAssists_ShouldFail() {
//        OrderLocation startOrderLocation = OrderLocationTestbuilder
//                .init()
//                .withDefaultValues()
//                .build();
//
//        Order order = OrderTestBuilder
//                .init()
//                .withDefaultValues()
//                .startOrderLocation(startOrderLocation)
//                .build();
//
//        List<Long> idList =  generateMockAssistance(17);
//
//        assertThrows(
//                OrderException.MaxOrderAssistsException.class,
//                () -> orderService.createOrder(order, idList)
//        );
//    }

//    @Test
//    public void createOrderWithNonExistingAssistance_ShouldFail() {
//
//        Assistance assistance = Assistance
//                .builder()
//                .id(1L)
//                .build();
//
//        OrderLocation startOrderLocation = OrderLocationTestbuilder
//                .init()
//                .withDefaultValues()
//                .build();
//
//        Order order = OrderTestBuilder
//                .init()
//                .withDefaultValues()
//                .services(Collections.singletonList(assistance))
//                .startOrderLocation(startOrderLocation)
//                .build();
//
//        when(assistanceService.getAssist(assistance.getId())).thenThrow(Exception.NotFoundException.class);
//
//        assertThrows(
//                Exception.NotFoundException.class,
//                () -> orderService.createOrder(order, List.of())
//        );
//    }

    @Test
    public void closeOrder_shouldSucceed() {
        Assistance assistance = AssistanceTestBuilder
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
                .services(Collections.singletonList(assistance))
                .startOrderLocation(orderLocation)
                .build();

        Order order = OrderTestBuilder
                .init()
                .withDefaultValues()
                .services(Collections.singletonList(assistance))
                .startOrderLocation(orderLocation)
                .endOrderLocation(orderLocation)
                .build();

        when(orderRepository.getById(order.getId())).thenReturn(orderSaved);
        when(orderRepository.save(orderSaved)).thenReturn(orderSaved);

        Order orderReturned = orderService.closeOrder(order);

        assertEquals(orderReturned.getEndOrderLocation(), orderReturned.getEndOrderLocation());
        assertEquals(orderReturned.getStartOrderLocation(), order.getStartOrderLocation());
        assertEquals(orderReturned.getServices(), order.getServices());
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

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2, order3));

        List<Order> orderList = orderService.listOrders();

        assertEquals(orderList.size(), 3);
        assertTrue(orderList.contains(order1));
        assertTrue(orderList.contains(order2));
        assertTrue(orderList.contains(order3));
    }

    @Test
    public void listOrderWithNoOrdersSaved_shouldSucceed() {
        when(orderRepository.findAll()).thenReturn(List.of());

        List<Order> orderList = orderService.listOrders();

        assertEquals(orderList.size(), 0);
    }

    @Test
    public void getOrder_shouldSucceed() {
        Order order =  OrderTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Optional<Order> orderOptional = Optional.ofNullable(order);

        when(orderRepository.findById(order.getId())).thenReturn(orderOptional);

        Order orderFound = orderService.getOrder(order.getId());

        assertEquals(orderFound.getOperatorId(), order.getOperatorId());
    }

    @Test
    public void getOrder_shouldFail() {

        when(orderRepository.findById(1L)).thenThrow(GenericException.NotFoundException.class);;

        assertThrows(
                GenericException.NotFoundException.class,
                () -> orderService.getOrder(1L)
        );
    }

    private List<Long> generateMockAssistance(int number) {
        ArrayList<Long> list = new ArrayList<>();

        for (int j = 0; j < number; j++) {
            list.add(Integer.toUnsignedLong(j));
        }

        return list;
    }
}
