package csi.sistema_agendamentos.service;
import csi.sistema_agendamentos.dto.AgendamentosDTO;
import csi.sistema_agendamentos.model.Agendamentos;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.model.Usuario;
import csi.sistema_agendamentos.repository.AgendamentosRepository;
import csi.sistema_agendamentos.repository.SalaRepository;
import csi.sistema_agendamentos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentosServiceTest {

    public AgendamentosServiceTest() {

    }

    @Mock
    private AgendamentosRepository agendamentosRepository;
    @Mock
    private SalaRepository salaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AgendamentosService agendamentosService;

    private Sala sala;
    private Usuario usuario;
    private Agendamentos agendamento;
    private AgendamentosDTO agendamentoDTO;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    @BeforeEach
    void setUp() {
        inicio = LocalDateTime.of(2025, 10, 1, 14, 0);
        fim = LocalDateTime.of(2025, 10, 1, 15, 0);

        sala = new Sala();
        sala.setIdSala(1);
        sala.setNomeSala("Sala Teste");

        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNome("Usuario Teste");

        agendamento = new Agendamentos(1, inicio, fim, "CONFIRMADO", usuario, sala);

        agendamentoDTO = new AgendamentosDTO();
        agendamentoDTO.setDataInicio(inicio);
        agendamentoDTO.setDataFim(fim);
        agendamentoDTO.setStatus("CONFIRMADO");
        agendamentoDTO.setSalaId(1);
        agendamentoDTO.setUsuarioId(1);
    }

    @Test
    void listarTodosAgendamentoDTO() {

        when(agendamentosRepository.findAll()).thenReturn(Collections.singletonList(agendamento));

        List<AgendamentosDTO> resultado = agendamentosService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(agendamentosRepository, times(1)).findAll();
    }

    @Test
    void buscarAgendamentoDTOPorId() {
        when(agendamentosRepository.findById(1)).thenReturn(Optional.of(agendamento));
        AgendamentosDTO resultado = agendamentosService.buscarPorId(1);
        assertNotNull(resultado);
    }

    @Test
    void buscarrAgendamentoDTOPorIdInvalido() {
        when(agendamentosRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            agendamentosService.buscarPorId(99);
        });
    }

    @Test
    void criarAgendamentoValido() {
        AgendamentosDTO novoDto = new AgendamentosDTO();
        novoDto.setDataInicio(inicio);
        novoDto.setDataFim(fim);
        novoDto.setSalaId(1);
        novoDto.setUsuarioId(1);

        when(salaRepository.findById(1)).thenReturn(Optional.of(sala));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(agendamentosRepository.save(any(Agendamentos.class))).thenReturn(agendamento);

        AgendamentosDTO resultado = agendamentosService.criarAgendamento(novoDto);

        assertNotNull(resultado);
        assertEquals("CONFIRMADO", resultado.getStatus());
        verify(agendamentosRepository, times(1)).save(any(Agendamentos.class));
    }

    @Test
    void atualizarAgendamento() {

        LocalDateTime novoFim = fim.plusHours(1);
        agendamentoDTO.setDataFim(novoFim);

        when(agendamentosRepository.findById(1)).thenReturn(Optional.of(agendamento));
        when(agendamentosRepository.save(any(Agendamentos.class))).thenReturn(agendamento);

        AgendamentosDTO resultado = agendamentosService.atualizarAgendamento(1, agendamentoDTO);

        assertNotNull(resultado);
        assertEquals(novoFim, resultado.getDataFim());
        verify(agendamentosRepository, times(1)).save(agendamento);
    }

    @Test
    void cancelarAgendamento() {

        when(agendamentosRepository.findById(1)).thenReturn(Optional.of(agendamento));
        when(agendamentosRepository.save(any(Agendamentos.class))).thenReturn(agendamento);

        agendamentosService.cancelarAgendamento(1);

        assertEquals("CANCELADO", agendamento.getStatus());
        verify(agendamentosRepository, times(1)).findById(1);
        verify(agendamentosRepository, times(1)).save(agendamento);
    }
}