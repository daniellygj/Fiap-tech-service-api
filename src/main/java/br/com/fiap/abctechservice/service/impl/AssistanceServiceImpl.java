package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.repository.AssistanceRepository;
import br.com.fiap.abctechservice.service.AssistanceService;
import br.com.fiap.abctechservice.handler.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssistanceServiceImpl implements AssistanceService {

    private final AssistanceRepository repository;

    public AssistanceServiceImpl(@Autowired AssistanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Assistance> getAssistsList() {
        return repository.findAll();
    }

    @Override
    public Assistance getAssist(Long id) {
        return repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Assistance"));
    }
}
