package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.model.Assistance;
import br.com.fiap.abctechservice.model.AssistanceTestBuilder;
import br.com.fiap.abctechservice.repository.AssistanceRepository;
import br.com.fiap.abctechservice.service.impl.AssistanceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;


@SpringBootTest
public class AssistanceServiceTest {

    @Mock
    private AssistanceRepository assistanceRepository;

    private AssistanceService assistanceService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        assistanceService = new AssistanceServiceImpl(assistanceRepository);
    }

    @Test
    public void listWithDataAssistance_ShouldSucceed() {
        Assistance itemAssist1 = AssistanceTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Assistance itemAssist2 = AssistanceTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();


        when(assistanceRepository.findAll()).thenReturn(List.of(itemAssist1, itemAssist2));

        List<Assistance> values = assistanceService.getAssistsList();

        assertEquals(values.size(), 2);
        assertSame(values.get(0), itemAssist1);
        assertSame(values.get(1), itemAssist2);
    }

    @Test
    public void listWithNoDataAssistance_ShouldSucceed() {

        when(assistanceRepository.findAll()).thenReturn(List.of());

        List<Assistance> values = assistanceService.getAssistsList();

        assertEquals(values.size(), 0);
    }
}
