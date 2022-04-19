package br.com.fiap.abctechservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderDto {

    private Long id;

    @NotNull
    @Positive
    private Long operatorId;

    @NotEmpty
    @NotNull
    private List<TaskDto> tasks;

    private OrderLocationDto startOrderLocation;

    private OrderLocationDto endOrderLocation;
}
