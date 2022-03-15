package br.com.fiap.abctechservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "order_location")
@Getter
@Setter
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
