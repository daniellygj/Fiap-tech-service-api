package br.com.fiap.abctechservice.model;

import br.com.fiap.abctechservice.model.Order.OrderBuilder;

public class OrderTestBuilder {

    private static final long ID = 1;
    private static final long OPERATOR_ID = 1;

    private OrderBuilder builder = Order.builder();

    public static OrderTestBuilder init() {
        return new OrderTestBuilder();
    }

    public OrderBuilder withDefaultValues() {
        return builder
                .id(ID)
                .operatorId(OPERATOR_ID);

    }

}
