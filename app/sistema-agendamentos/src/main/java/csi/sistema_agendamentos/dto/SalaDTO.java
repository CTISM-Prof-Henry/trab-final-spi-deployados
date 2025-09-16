package csi.sistema_agendamentos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaDTO {

    private String nomeSala;
    private String tipoSala;
    private String predio;
    private String complemento;
    private int capacidade;
    private Boolean ativo;
}