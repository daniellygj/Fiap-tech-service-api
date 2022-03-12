package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    private VersionService versionService;

    public HealthCheckController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Success!");
    }

    @GetMapping("version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok(versionService.getVersion());
    }
}
