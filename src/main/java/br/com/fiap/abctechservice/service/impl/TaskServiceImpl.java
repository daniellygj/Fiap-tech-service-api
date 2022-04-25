package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.dto.TaskDto;
import br.com.fiap.abctechservice.handler.exception.NotFoundException;
import br.com.fiap.abctechservice.model.Task;
import br.com.fiap.abctechservice.repository.TaskRepository;
import br.com.fiap.abctechservice.service.TaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.List;


@org.springframework.stereotype.Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    private final ModelMapper mapper;

    public TaskServiceImpl(@Autowired TaskRepository repository, @Autowired ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TaskDto> getTasksByOrder(Long orderId) {
        List<Task> tasks = repository.findAllByOrderId(orderId);
        Type listType = new TypeToken<List<TaskDto>>(){}.getType();
        return mapper.map(tasks, listType);
    }

    @Override
    public List<TaskDto> getTasks() {
        List<Task> tasks = repository.findAll();
        Type listType = new TypeToken<List<TaskDto>>(){}.getType();
        return mapper.map(tasks, listType);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task", id));
        return mapper.map(task, TaskDto.class);
    }
}
