package csi.sistema_agendamentos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_agendamento;
    private LocalDateTime data_inicio;
    private LocalDateTime data_fim;
    private String status;

    @ManyToOne
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cod_sala")
    private Sala sala;

}

