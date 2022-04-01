package br.com.fiap.abctechservice.application.dto;

import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.model.OrderLocation;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull
    @Positive
    private Long operatorId;

    @NotEmpty
    @NotNull
    private List<Assistance> services;

    private OrderLocationDto startOrderLocation;

    private OrderLocationDto endOrderLocation;

}
