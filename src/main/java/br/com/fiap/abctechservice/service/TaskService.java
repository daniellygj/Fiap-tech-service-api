package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> getTasksByOrder(Long orderId);

    List<TaskDto> getTasks();

    TaskDto getTaskById(Long id);
}
