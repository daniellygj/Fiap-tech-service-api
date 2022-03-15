package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.service.AssistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assistance")
public class AssistanceController {

    private final AssistanceService assistanceService;

    public AssistanceController(@Autowired AssistanceService assistanceService) {
        this.assistanceService = assistanceService;
    }

    @GetMapping
    public ResponseEntity<List<Assistance>> getAssists() {
        List<Assistance> list = this.assistanceService.getAssistsList();
        return ResponseEntity.ok(list);
    }

}
