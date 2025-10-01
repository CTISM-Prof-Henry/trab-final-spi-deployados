package csi.sistema_agendamentos.repository;

import csi.sistema_agendamentos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    List<Sala> findByComplementoContainingIgnoreCase(String complemento);
}
