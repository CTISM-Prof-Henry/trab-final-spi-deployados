package csi.sistema_agendamentos.repository;

import csi.sistema_agendamentos.model.Agendamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentosRepository extends JpaRepository<Agendamentos, Integer> {

    List<Agendamentos> findBySalaIdSala(Integer idSala);

    @Query("SELECT a FROM Agendamentos a " +
            "WHERE a.sala.idSala = :salaId " +
            "AND a.status = :status " +
            "AND a.data_inicio <= :dataFim " +
            "AND a.data_fim >= :dataInicio")
    List<Agendamentos> findConflitos(
            @Param("salaId") Integer salaId,
            @Param("status") String status,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );


}