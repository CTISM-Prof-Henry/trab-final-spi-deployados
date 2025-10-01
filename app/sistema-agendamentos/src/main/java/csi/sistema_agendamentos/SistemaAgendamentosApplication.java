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
import java.util.List;

@SpringBootApplication
public class SistemaAgendamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaAgendamentosApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UsuarioRepository UsuarioRepository, SalaRepository salaRepository, AgendamentosRepository agendamentosRepository) {
		return (args) -> {

        };
}
}
