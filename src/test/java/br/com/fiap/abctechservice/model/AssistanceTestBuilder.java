package br.com.fiap.abctechservice.model;

import br.com.fiap.abctechservice.model.Assistance.AssistanceBuilder;

public class AssistanceTestBuilder {

    private static final long ID = 1;
    private static final String NAME = "Mocked name";
    private static final String DESCRIPTION = "Mocked description";

    private  AssistanceBuilder builder = Assistance.builder();

    public static AssistanceTestBuilder init() {
        return new AssistanceTestBuilder();
    }

    public AssistanceBuilder withDefaultValues() {
        return builder
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION);
    }

}
