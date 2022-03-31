package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.application.AssistanceApplication;
import br.com.fiap.abctechservice.application.dto.AssistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assistance")
public class AssistanceController {

    private final AssistanceApplication assistanceApplication;

    public AssistanceController(@Autowired AssistanceApplication application) {
        this.assistanceApplication = application;
    }

    @GetMapping
    public ResponseEntity<List<AssistDto>> getAssists() {
        List<AssistDto> list = this.assistanceApplication.getAssists();
        return ResponseEntity.ok(list);
    }

}
