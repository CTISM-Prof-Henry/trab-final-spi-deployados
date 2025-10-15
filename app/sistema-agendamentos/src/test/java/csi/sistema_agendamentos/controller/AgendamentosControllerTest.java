package csi.sistema_agendamentos.controller;

import csi.sistema_agendamentos.dto.AgendamentosDTO;
import csi.sistema_agendamentos.service.AgendamentosService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste de unidade puro para AgendamentosController.
 * Testa a lógica do controller de forma isolada, sem carregar o contexto do Spring.
 * É mais rápido e focado que testes com @WebMvcTest.
 */
@ExtendWith(MockitoExtension.class)
class AgendamentosControllerTest {

    @Mock
    private AgendamentosService agendamentoService;

    @InjectMocks
    private AgendamentosController agendamentosController;

    private AgendamentosDTO agendamentoDTO;

    @BeforeEach
    void setUp() {

        agendamentoDTO = new AgendamentosDTO();
        agendamentoDTO.setId(1);
        agendamentoDTO.setDataInicio(LocalDateTime.now().plusDays(1));
        agendamentoDTO.setDataFim(LocalDateTime.now().plusDays(1).plusHours(1));
        agendamentoDTO.setStatus("CONFIRMADO");
        agendamentoDTO.setSalaId(1);
        agendamentoDTO.setUsuarioId(1);
    }

    @Test
    void criarAgendamentoValido() {

        when(agendamentoService.criarAgendamento(any(AgendamentosDTO.class))).thenReturn(agendamentoDTO);


        ResponseEntity<?> resposta = agendamentosController.criar(agendamentoDTO);


        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals(agendamentoDTO.getId(), ((AgendamentosDTO) resposta.getBody()).getId());
    }

    @Test
    void criarAgendamentoComConflito() {

        String mensagemErro = "Horário indisponível para esta sala.";
        when(agendamentoService.criarAgendamento(any(AgendamentosDTO.class)))
                .thenThrow(new IllegalArgumentException(mensagemErro));


        ResponseEntity<?> resposta = agendamentosController.criar(agendamentoDTO);


        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals(mensagemErro, resposta.getBody());
    }

    @Test
    void listarTodos() {

        when(agendamentoService.listarTodos()).thenReturn(Collections.singletonList(agendamentoDTO));


        ResponseEntity<List<AgendamentosDTO>> resposta = agendamentosController.listarTodos();


        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    void buscarPorIdExistente() {

        when(agendamentoService.buscarPorId(1)).thenReturn(agendamentoDTO);


        ResponseEntity<?> resposta = agendamentosController.buscarPorId(1);


        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void buscarPorIdInexistente() {

        String mensagemErro = "Agendamento não encontrado.";
        when(agendamentoService.buscarPorId(99)).thenThrow(new EntityNotFoundException(mensagemErro));


        ResponseEntity<?> resposta = agendamentosController.buscarPorId(99);


        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals(mensagemErro, resposta.getBody());
    }

    @Test
    void listarPorSala() {

        when(agendamentoService.listarPorSala(1)).thenReturn(Collections.singletonList(agendamentoDTO));


        ResponseEntity<List<AgendamentosDTO>> resposta = agendamentosController.listarPorSala(1);


        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    void atualizarAgendamento() {

        when(agendamentoService.atualizarAgendamento(eq(1), any(AgendamentosDTO.class))).thenReturn(agendamentoDTO);


        ResponseEntity<AgendamentosDTO> resposta = agendamentosController.atualizar(1, agendamentoDTO);


        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void cancelarAgendamento() {

        doNothing().when(agendamentoService).cancelarAgendamento(1);


        ResponseEntity<Void> resposta = agendamentosController.cancelar(1);


        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(agendamentoService, times(1)).cancelarAgendamento(1); // Garante que o método foi chamado
    }
}