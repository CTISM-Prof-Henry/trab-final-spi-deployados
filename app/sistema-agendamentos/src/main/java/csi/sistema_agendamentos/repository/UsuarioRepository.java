package csi.sistema_agendamentos.repository;

import csi.sistema_agendamentos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
