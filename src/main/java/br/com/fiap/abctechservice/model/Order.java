package br.com.fiap.abctechservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long operatorId;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinTable(
            name = "order_task",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "start_order_location_id")
    private OrderLocation startOrderLocation;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "end_order_location_id")
    private OrderLocation endOrderLocation;
}
