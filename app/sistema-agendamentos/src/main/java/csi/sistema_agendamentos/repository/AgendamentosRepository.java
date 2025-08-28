package csi.sistema_agendamentos.repository;

import csi.sistema_agendamentos.model.Agendamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentosRepository extends JpaRepository<Agendamentos, Integer> {
}
