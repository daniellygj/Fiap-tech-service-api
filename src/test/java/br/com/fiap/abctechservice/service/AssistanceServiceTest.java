package br.com.fiap.abctechservice.service;

import br.com.fiap.abctechservice.model.Assistance;
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
    public void listWithDataAssistanceShouldSucceed() {
        Assistance itemAssist = new Assistance(1L, "Mock name", "Mock description");
        Assistance itemAssist2 = new Assistance(2L, "Mock name 2", "Mock description 2");

        when(assistanceRepository.findAll()).thenReturn(List.of(itemAssist, itemAssist2));

        List<Assistance> values = assistanceService.getAssistsList();

        assertEquals(values.size(), 2);
        assertSame(values.get(0), itemAssist);
        assertSame(values.get(1), itemAssist2);
    }

    @Test
    public void listWithNoDataAssistanceShouldSucceed() {

        when(assistanceRepository.findAll()).thenReturn(List.of());

        List<Assistance> values = assistanceService.getAssistsList();

        assertEquals(values.size(), 0);
    }
}
