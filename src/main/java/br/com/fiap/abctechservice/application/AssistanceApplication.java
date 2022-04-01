package br.com.fiap.abctechservice.application;

import br.com.fiap.abctechservice.application.dto.AssistDto;

import java.util.List;

public interface AssistanceApplication {

    List<AssistDto> getAssists();

    AssistDto getAssitById(Long id);

}
