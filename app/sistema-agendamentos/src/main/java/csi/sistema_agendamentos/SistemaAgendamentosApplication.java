package csi.sistema_agendamentos;

import csi.sistema_agendamentos.model.Agendamentos;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.model.Usuario;
import csi.sistema_agendamentos.repository.AgendamentosRepository;
import csi.sistema_agendamentos.repository.SalaRepository;
import csi.sistema_agendamentos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class SistemaAgendamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaAgendamentosApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UsuarioRepository UsuarioRepository, SalaRepository salaRepository, AgendamentosRepository agendamentosRepository) {
		return (args) -> {


            try {
                Usuario usuario = new Usuario();
                usuario.setNome("Usuario 2");
                usuario.setSenha("123456");
                usuario.setCpf("123676");
                usuario.setEmail("usuario2@email.com");
                usuario.setTipo_usuario(1);
                usuario.setMatricula("1756");

                UsuarioRepository.save(usuario);

                Sala sala = new Sala();
                sala.setNome_sala("F-208");
                sala.setAtivo(true);
                sala.setTipo_sala("Laboratorio");
                sala.setCapacidade(30);
                sala.setPredio("Politécnico");
                sala.setComplemento("Bloco F");

                salaRepository.save(sala);


                Agendamentos agendamento = new Agendamentos();
                agendamento.setData_inicio(LocalDateTime.of(2025, 8, 29, 14, 0));
                agendamento.setData_fim(LocalDateTime.of(2025, 8, 29, 16, 0));
                agendamento.setStatus("Ativo");
                agendamento.setUsuario(usuario);
                agendamento.setSala(sala);
                agendamentosRepository.save(agendamento);
            } catch (Exception e) {
				System.out.println("Usuário já inserido");
            }

        };
}
}
