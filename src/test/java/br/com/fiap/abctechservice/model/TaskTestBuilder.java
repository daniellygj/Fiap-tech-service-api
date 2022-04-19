package br.com.fiap.abctechservice.model;

import br.com.fiap.abctechservice.model.Task.TaskBuilder;

public class TaskTestBuilder {

    private static final long ID = 1;
    private static final String NAME = "Mocked name";
    private static final String DESCRIPTION = "Mocked description";

    private  TaskBuilder builder = Task.builder();

    public static TaskTestBuilder init() {
        return new TaskTestBuilder();
    }

    public TaskBuilder withDefaultValues() {
        return builder
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION);
    }
}
