package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.model.Assistance;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface AssistanceService {

    List<Assistance> getAssistsList();

    Assistance getAssist(Long id);
}
