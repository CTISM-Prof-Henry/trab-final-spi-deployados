package csi.sistema_agendamentos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import csi.sistema_agendamentos.dto.AgendamentosDTO;
import csi.sistema_agendamentos.repository.AgendamentosRepository;
import csi.sistema_agendamentos.repository.SalaRepository;
import csi.sistema_agendamentos.repository.UsuarioRepository;
import csi.sistema_agendamentos.service.AgendamentosService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentosController.class) // Testa apenas a camada web para este controller
class AgendamentosControllerTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular requisições HTTP (POST, GET, etc.)

    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos Java em JSON e vice-versa

    @MockBean // Cria um "dublê" do serviço. Não usamos a lógica real do service, nós a simulamos.
    private AgendamentosService agendamentosService;

    @MockBean private AgendamentosRepository agendamentosRepository;
    @MockBean private SalaRepository salaRepository;
    @MockBean private UsuarioRepository usuarioRepository;

    private AgendamentosDTO agendamentoDTO;

    @BeforeEach
    void setUp() {
        agendamentoDTO = new AgendamentosDTO();
        agendamentoDTO.setId(1);
        agendamentoDTO.setDataInicio(LocalDateTime.of(2025, 9, 14, 20, 0, 0));
        agendamentoDTO.setDataFim(LocalDateTime.of(2025, 9, 14, 21, 0, 0));
        agendamentoDTO.setStatus("CONFIRMADO");
        agendamentoDTO.setSalaId(1);
        agendamentoDTO.setUsuarioId(1);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEAgendamento() throws Exception {

        when(agendamentosService.criarAgendamento(any(AgendamentosDTO.class))).thenReturn(agendamentoDTO);

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamentoDTO)))
                .andExpect(status().isCreated()) // Espera HTTP 201
                .andExpect(jsonPath("$.id").value(1)) // Verifica o JSON de resposta
                .andExpect(jsonPath("$.status").value("CONFIRMADO"));
    }

    @Test
    void listarTodos_DeveRetornarStatusOKeListaDeAgendamentos() throws Exception {
        when(agendamentosService.listarTodos()).thenReturn(Collections.singletonList(agendamentoDTO));

        mockMvc.perform(get("/agendamentos"))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoEncontrado_DeveRetornarStatusOKeAgendamento() throws Exception {
        when(agendamentosService.buscarPorId(1)).thenReturn(agendamentoDTO);

        mockMvc.perform(get("/agendamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoEncontrado_DeveRetornarStatusNotFound() throws Exception {
        when(agendamentosService.buscarPorId(99)).thenThrow(new EntityNotFoundException("Não encontrado"));

        mockMvc.perform(get("/agendamentos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornarStatusOKeAgendamentoAtualizado() throws Exception {
        when(agendamentosService.atualizarAgendamento(eq(1), any(AgendamentosDTO.class))).thenReturn(agendamentoDTO);

        mockMvc.perform(put("/agendamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamentoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMADO"));
    }

    @Test
    void cancelar_DeveRetornarStatusNoContent() throws Exception {
        doNothing().when(agendamentosService).cancelarAgendamento(1);
        mockMvc.perform(delete("/agendamentos/1"))
                .andExpect(status().isNoContent()); // Espera HTTP 204
    }
}