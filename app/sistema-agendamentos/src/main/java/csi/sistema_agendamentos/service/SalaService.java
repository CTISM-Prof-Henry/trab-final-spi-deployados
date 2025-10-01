package csi.sistema_agendamentos.service;

import csi.sistema_agendamentos.dto.SalaDTO;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import recomendado

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    private SalaDTO toDTO(Sala sala) {
        SalaDTO dto = new SalaDTO();
        dto.setIdSala(sala.getIdSala());
        dto.setNomeSala(sala.getNomeSala());
        dto.setTipoSala(sala.getTipoSala());
        dto.setPredio(sala.getPredio());
        dto.setComplemento(sala.getComplemento());
        dto.setCapacidade(sala.getCapacidade());
        dto.setAtivo(sala.isAtivo());
        return dto;
    }

    private Sala toEntity(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setNomeSala(dto.getNomeSala());
        sala.setTipoSala(dto.getTipoSala());
        sala.setPredio(dto.getPredio());
        sala.setComplemento(dto.getComplemento());
        sala.setCapacidade(dto.getCapacidade());
        sala.setAtivo(dto.getAtivo());
        return sala;
    }

    @Transactional(readOnly = true)
    public List<SalaDTO> listarTodas() {
        return salaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Sala> listarSalasPorBloco(String bloco) {
        return salaRepository.findByComplementoContainingIgnoreCase(bloco);
    }

    @Transactional(readOnly = true)
    public Optional<SalaDTO> buscarPorId(Integer id) {
        return salaRepository.findById(id)
                .map(this::toDTO);
    }

    @Transactional
    public SalaDTO salvar(SalaDTO salaDTO) {
        Sala salaParaSalvar = toEntity(salaDTO);
        Sala salaSalva = salaRepository.save(salaParaSalvar);
        return toDTO(salaSalva);
    }

    @Transactional
    public SalaDTO atualizar(Integer id, SalaDTO salaDTOAtualizada) {
        return salaRepository.findById(id).map(salaExistente -> {
            salaExistente.setNomeSala(salaDTOAtualizada.getNomeSala());
            salaExistente.setTipoSala(salaDTOAtualizada.getTipoSala());
            salaExistente.setPredio(salaDTOAtualizada.getPredio());
            salaExistente.setComplemento(salaDTOAtualizada.getComplemento());
            salaExistente.setCapacidade(salaDTOAtualizada.getCapacidade());
            salaExistente.setAtivo(salaDTOAtualizada.getAtivo());

            Sala salaAtualizada = salaRepository.save(salaExistente);
            return toDTO(salaAtualizada);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada com id " + id));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala não encontrada com id " + id);
        }
        salaRepository.deleteById(id);
    }
}