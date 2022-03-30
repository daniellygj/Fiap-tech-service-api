package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.model.Order;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order closeOrder(Order order);

    List<Order> listOrders();

    Order getOrder(Long id) ;
}
