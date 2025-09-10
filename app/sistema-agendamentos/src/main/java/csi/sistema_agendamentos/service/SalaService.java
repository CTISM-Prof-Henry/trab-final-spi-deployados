package csi.sistema_agendamentos.service;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.repository.SalaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    // Constructor Injection (recommended)
    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Integer id) {
        return salaRepository.findById(id);
    }

    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala atualizar(Integer id, Sala salaAtualizada) {
        return salaRepository.findById(id).map(sala -> {
            sala.setNomeSala(salaAtualizada.getNomeSala());
            sala.setTipoSala(salaAtualizada.getTipoSala());
            sala.setPredio(salaAtualizada.getPredio());
            sala.setComplemento(salaAtualizada.getComplemento());
            sala.setCapacidade(salaAtualizada.getCapacidade());
            sala.setAtivo(salaAtualizada.isAtivo());
            return salaRepository.save(sala);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada com id " + id));
    }

    public void deletar(Integer id) {
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sala não encontrada com id " + id);
        }
    }
}
