package csi.sistema_agendamentos.service;

import csi.sistema_agendamentos.dto.AgendamentosDTO;
import csi.sistema_agendamentos.model.Agendamentos;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.model.Usuario;
import csi.sistema_agendamentos.repository.AgendamentosRepository;
import csi.sistema_agendamentos.repository.SalaRepository;
import csi.sistema_agendamentos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentosService {

    private final AgendamentosRepository agendamentosRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    public AgendamentosService(AgendamentosRepository agendamentosRepository, SalaRepository salaRepository, UsuarioRepository usuarioRepository) {
        this.agendamentosRepository = agendamentosRepository;
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AgendamentosDTO criarAgendamento(AgendamentosDTO dto) {

        Sala sala = salaRepository.findById(dto.getSalaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada com ID: " + dto.getSalaId()));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getUsuarioId()));

        Agendamentos agendamento = convertToEntity(dto, sala, usuario);
        agendamento.setStatus("Ativo");

        Agendamentos agendamentoSalvo = agendamentosRepository.save(agendamento);
        return convertToDTO(agendamentoSalvo);
    }

    @Transactional(readOnly = true)
    public List<AgendamentosDTO> listarTodos() {
        return agendamentosRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendamentosDTO buscarPorId(Integer id) {
        return agendamentosRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<AgendamentosDTO> listarPorSala(Integer salaId) {
        return agendamentosRepository.findBySalaIdSala(salaId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgendamentosDTO atualizarAgendamento(Integer id, AgendamentosDTO dto) {
        Agendamentos agendamentoExistente = agendamentosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));

        agendamentoExistente.setData_inicio(dto.getDataInicio());
        agendamentoExistente.setData_fim(dto.getDataFim());

        Agendamentos agendamentoAtualizado = agendamentosRepository.save(agendamentoExistente);
        return convertToDTO(agendamentoAtualizado);
    }


    @Transactional
    public void cancelarAgendamento(Integer id) {
        Agendamentos agendamento = agendamentosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));

        agendamento.setStatus("CANCELADO");
        agendamentosRepository.save(agendamento);
    }


    private AgendamentosDTO convertToDTO(Agendamentos agendamento) {
        AgendamentosDTO dto = new AgendamentosDTO();
        dto.setId(agendamento.getId_agendamento());
        dto.setDataInicio(agendamento.getData_inicio());
        dto.setDataFim(agendamento.getData_fim());
        dto.setStatus(agendamento.getStatus());
        dto.setSalaId(agendamento.getSala().getIdSala());
        dto.setUsuarioId(agendamento.getUsuario().getIdUsuario());
        dto.setUsuarioNome(agendamento.getUsuario().getNome());
        dto.setSalaNome(agendamento.getSala().getNomeSala());
        return dto;
    }

    private Agendamentos convertToEntity(AgendamentosDTO dto, Sala sala, Usuario usuario) {
        Agendamentos agendamento = new Agendamentos();
        agendamento.setData_inicio(dto.getDataInicio());
        agendamento.setData_fim(dto.getDataFim());
        agendamento.setStatus(dto.getStatus());
        agendamento.setSala(sala);
        agendamento.setUsuario(usuario);
        return agendamento;
    }
}