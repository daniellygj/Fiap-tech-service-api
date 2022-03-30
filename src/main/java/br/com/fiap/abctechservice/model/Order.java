package br.com.fiap.abctechservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long operatorId;

    @ManyToMany
    private List<Assistance> services;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "start_order_location_id")
    private OrderLocation startOrderLocation;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "end_order_location_id")
    private OrderLocation endOrderLocation;
}
