package br.com.fiap.abctechservice.model;

import br.com.fiap.abctechservice.model.Orders.OrdersBuilder;

public class OrderTestBuilder {

    private static final long ID = 1;
    private static final long OPERATOR_ID = 1;

    private OrdersBuilder builder = Orders.builder();

    public static OrderTestBuilder init() {
        return new OrderTestBuilder();
    }

    public OrdersBuilder withDefaultValues() {
        return builder
                .id(ID)
                .operatorId(OPERATOR_ID);

    }

}
