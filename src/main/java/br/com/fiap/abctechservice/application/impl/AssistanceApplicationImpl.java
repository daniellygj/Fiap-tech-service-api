package br.com.fiap.abctechservice.application.impl;

import br.com.fiap.abctechservice.application.AssistanceApplication;
import br.com.fiap.abctechservice.application.dto.AssistDto;
import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.service.AssistanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssistanceApplicationImpl implements AssistanceApplication {

    private final AssistanceService assistanceService;

    private ModelMapper mapper = new ModelMapper();

    public AssistanceApplicationImpl(@Autowired AssistanceService assistanceService) {
        this.assistanceService = assistanceService;
    }

    @Override
    public List<AssistDto> getAssists() {
        List<Assistance> assistanceList = this.assistanceService.getAssistsList();

        return assistanceList.stream()
                .map(assistance -> new AssistDto(assistance.getId(), assistance.getName(), assistance.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public AssistDto getAssitById(Long id) {
        Assistance assistance = assistanceService.getAssist(id);

        return mapper.map(assistance, AssistDto.class);
    }
}
