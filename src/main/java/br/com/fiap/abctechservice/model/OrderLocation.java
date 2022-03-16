package br.com.fiap.abctechservice.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "order_location")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer latitude;

    private Integer longitude;

    private Date date;

}
