package br.com.fiap.abctechservice.model;

import java.sql.Date;
import br.com.fiap.abctechservice.model.OrderLocation.OrderLocationBuilder;

public class OrderLocationTestbuilder {

    private static final long ID = 1;
    private static final Integer LATITUDE = 123;
    private static final Integer LONGITUDE = 123;
    private static final Date DATE = Date.valueOf("2021-10-28");

    private OrderLocationBuilder builder = OrderLocation.builder();

    public static OrderLocationTestbuilder init() {
        return new OrderLocationTestbuilder();
    }

    public OrderLocationBuilder withDefaultValues() {
        return builder
                .id(ID)
                .latitude(LATITUDE)
                .longitude(LONGITUDE)
                .date(DATE);
    }

}
