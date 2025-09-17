package csi.sistema_agendamentos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class LoginDTO {
    private String cpf;
    private String senha;
}
