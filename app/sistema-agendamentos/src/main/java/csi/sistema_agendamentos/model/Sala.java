package csi.sistema_agendamentos.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sala")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sala;
    private String nome_sala;
    private String tipo_sala;
    private String predio;
    private String complemento;
    private int capacidade;
    private boolean ativo;

}


