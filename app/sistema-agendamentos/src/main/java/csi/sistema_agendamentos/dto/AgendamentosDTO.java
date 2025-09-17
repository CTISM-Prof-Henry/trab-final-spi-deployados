package csi.sistema_agendamentos.dto;

import csi.sistema_agendamentos.model.Agendamentos;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentosDTO {

    private Integer id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String status;
    private Integer usuarioId;
    private String usuarioNome;
    private Integer salaId;
    private String salaNome;


}