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
    @Column(name = "id_sala")
    private Integer idSala;

    @Column(name = "nome_sala")
    private String nomeSala;

    @Column(name = "tipo_sala")
    private String tipoSala;

    @Column(name = "predio")
    private String predio;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "capacidade")
    private int capacidade;

    @Column(name = "ativo")
    private boolean ativo;

}


