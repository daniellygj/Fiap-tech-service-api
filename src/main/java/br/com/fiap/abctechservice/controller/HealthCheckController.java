package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.application.PropertiesComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    private final PropertiesComponent propertiesComponent;

    public HealthCheckController(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
    }

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Success!");
    }

    @GetMapping("version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok(propertiesComponent.getVersion());
    }
}
