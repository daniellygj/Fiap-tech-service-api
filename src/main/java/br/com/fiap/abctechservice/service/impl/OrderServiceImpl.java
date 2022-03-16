package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.model.Order;
import br.com.fiap.abctechservice.repository.OrderRepository;
import br.com.fiap.abctechservice.service.AssistanceService;
import br.com.fiap.abctechservice.service.OrderService;
import br.com.fiap.abctechservice.utils.exception.Exception;
import br.com.fiap.abctechservice.utils.exception.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final AssistanceService assistanceService;

    public OrderServiceImpl(@Autowired OrderRepository orderRepository, @Autowired AssistanceService assistanceService) {
        this.orderRepository = orderRepository;
        this.assistanceService = assistanceService;
    }

    @Override
    public Order createOrder(Order order) {
        // todo se nao tiver data de inicio, throw exception
        // todo nao deixar salvar EndOrderLocation

        List<Assistance> assistanceList = new ArrayList<>();

        int servicesQty = order.getServices() != null ? order.getServices().size() : 0;

        if (servicesQty < 1) {
            throw new OrderException.MinOrderAssistsException();
        } else if (servicesQty > 15) {
            throw new OrderException.MaxOrderAssistsException();
        }

        order.getServices().forEach(assistance -> {
        Assistance assistanceFound = assistanceService.getAssist(assistance.getId());

        if (!assistanceList.contains(assistanceFound)) {
            assistanceList.add(assistanceFound);
        }

        });

        order.setServices(assistanceList);

        return orderRepository.save(order);
    }

    @Override
    public Order closeOrder(Order order) {
        // todo verificar se a ordem ja não foi fechada ?
        // todo estourar erro se a ordem não existir
        // todo não esquecer dos testes quando fizer as alterações
        Order orderSaved = orderRepository.getById(order.getId());

        order.setEndOrderLocation(order.getEndOrderLocation());

        return orderRepository.save(orderSaved);
    }

    @Override
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new Exception.NotFoundException("Order"));
    }
}
