CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT NOT NULL,
                        operator_id BIGINT NOT NULL,
                        start_order_location_id BIGINT NULL,
                        end_order_location_id BIGINT NULL,
                        CONSTRAINT pk_orders PRIMARY KEY (id)
);


create table orders_services
(
    order_id       bigint not null,
    services_id bigint not null,
    constraint FK_ORDER_ASSISTANCE_ID
        foreign key (services_id) references assistances (id),
    constraint FK_ASSISTANCE_ORDER_ID
        foreign key (order_id) references orders (id)
);


CREATE TABLE order_location (
                                id BIGINT AUTO_INCREMENT NOT NULL,
                                latitude DOUBLE NULL,
                                longitude DOUBLE NULL,
                                date datetime NULL,
                                CONSTRAINT pk_orderlocation PRIMARY KEY (id)
);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_END_ORDER_LOCATION FOREIGN KEY (end_order_location_id) REFERENCES order_location (id);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_START_ORDER_LOCATION FOREIGN KEY (start_order_location_id) REFERENCES order_location (id);