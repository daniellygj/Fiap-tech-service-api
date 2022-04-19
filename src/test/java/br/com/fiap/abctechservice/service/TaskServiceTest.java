package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.dto.OrderDto;
import br.com.fiap.abctechservice.dto.TaskDto;
import br.com.fiap.abctechservice.model.Order;
import br.com.fiap.abctechservice.model.Task;
import br.com.fiap.abctechservice.model.TaskTestBuilder;
import br.com.fiap.abctechservice.repository.TaskRepository;
import br.com.fiap.abctechservice.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;
    
    private final ModelMapper mapper = new ModelMapper();

    private TaskService taskService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository, modelMapper);
    }

    @Test
    public void listWithDataTask_ShouldSucceed() {
        Task task1 = TaskTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Task task2 = TaskTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        Type listType = new TypeToken<List<TaskDto>>(){}.getType();
        List<Task> tasks = List.of(task1, task2);
        List<TaskDto> taskDtos = mapper.map(tasks, listType);

        when(taskRepository.findAllByOrderId(1L)).thenReturn(tasks);
        when(modelMapper.map(tasks, listType)).thenReturn(taskDtos);

        List<TaskDto> values = taskService.getTasksByOrder(1L);

        assertEquals(values.size(), 2);
        assertTrue(values.contains(taskDtos.get(0)));
        assertTrue(values.contains(taskDtos.get(1)));
    }

    @Test
    public void listWithNoDataService_ShouldSucceed() {
        Type listType = new TypeToken<List<TaskDto>>(){}.getType();
        List<Task> tasks = List.of();
        List<TaskDto> taskDtos = mapper.map(tasks, listType);

        when(taskRepository.findAll()).thenReturn(List.of());
        when(modelMapper.map(tasks, listType)).thenReturn(taskDtos);

        List<TaskDto> values = taskService.getTasksByOrder(1L);

        assertEquals(values.size(), 0);
    }
}
