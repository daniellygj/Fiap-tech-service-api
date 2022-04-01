package br.com.fiap.abctechservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    private Double latitude;

    private Double longitude;

    private Date date;

}
