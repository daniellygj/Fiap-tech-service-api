package br.com.fiap.abctechservice.controller;

import br.com.fiap.abctechservice.dto.TaskDto;
import br.com.fiap.abctechservice.model.Task;
import br.com.fiap.abctechservice.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @GetMapping("{orderId}")
    public ResponseEntity<List<TaskDto>> getTask(@PathVariable("orderId") Long orderId) {
        List<TaskDto> list = service.getTasksByOrder(orderId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getTaskById(id));
    }

}
