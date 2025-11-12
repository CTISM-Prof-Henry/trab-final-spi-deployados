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

    private SalaDTO toDTO(final Sala sala) {
        final SalaDTO dto = new SalaDTO();
        dto.setIdSala(sala.getIdSala());
        dto.setNomeSala(sala.getNomeSala());
        dto.setTipoSala(sala.getTipoSala());
        dto.setPredio(sala.getPredio());
        dto.setComplemento(sala.getComplemento());
        dto.setCapacidade(sala.getCapacidade());
        dto.setAtivo(sala.isAtivo());
        return dto;
    }

    private Sala toEntity(final SalaDTO dto) {
        final Sala sala = new Sala();
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
    public List<Sala> listarSalasPorBloco(final String bloco) {
        return salaRepository.findByComplementoContainingIgnoreCase(bloco);
    }

    @Transactional(readOnly = true)
    public Optional<SalaDTO> buscarPorId(final Integer id) {
        return salaRepository.findById(id)
                .map(this::toDTO);
    }

    @Transactional
    public SalaDTO salvar(final SalaDTO salaDTO) {
        final Sala salaParaSalvar = toEntity(salaDTO);
        final Sala salaSalva = salaRepository.save(salaParaSalvar);
        return toDTO(salaSalva);
    }

    @Transactional
    public SalaDTO atualizar(final Integer id, final SalaDTO salaDTOAtualizada) {
        return salaRepository.findById(id).map(salaExistente -> {
            salaExistente.setNomeSala(salaDTOAtualizada.getNomeSala());
            salaExistente.setTipoSala(salaDTOAtualizada.getTipoSala());
            salaExistente.setPredio(salaDTOAtualizada.getPredio());
            salaExistente.setComplemento(salaDTOAtualizada.getComplemento());
            salaExistente.setCapacidade(salaDTOAtualizada.getCapacidade());
            salaExistente.setAtivo(salaDTOAtualizada.getAtivo());

            final Sala salaAtualizada = salaRepository.save(salaExistente);
            return toDTO(salaAtualizada);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada com id " + id));
    }

    @Transactional
    public void deletar(final Integer id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala não encontrada com id " + id);
        }
        salaRepository.deleteById(id);
    }

    @Transactional
    public SalaDTO atualizarStatusAtivo(Integer id, boolean ativo) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada com id " + id));

        sala.setAtivo(ativo);
        Sala salaSalva = salaRepository.save(sala);
        return toDTO(salaSalva);
    }
}