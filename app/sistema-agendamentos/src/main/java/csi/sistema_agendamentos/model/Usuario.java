package csi.sistema_agendamentos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
    private int tipo_usuario;
    private String nome;
    private String cpf;
    private String matricula;
    private String email;
    private String senha;

}



