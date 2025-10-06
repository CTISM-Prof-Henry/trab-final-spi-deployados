package csi.sistema_agendamentos.controller;

import csi.sistema_agendamentos.dto.AgendamentosDTO;
import csi.sistema_agendamentos.service.AgendamentosService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "http://localhost:4200")
public class AgendamentosController {

    private final AgendamentosService agendamentosService;

    public AgendamentosController(AgendamentosService agendamentoService) {
        this.agendamentosService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AgendamentosDTO dto) {
        try {
            AgendamentosDTO agendamentoCriado = agendamentosService.criarAgendamento(dto);
            return new ResponseEntity<>(agendamentoCriado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<List<AgendamentosDTO>> listarTodos() {
        List<AgendamentosDTO> agendamentos = agendamentosService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            AgendamentosDTO agendamento = agendamentosService.buscarPorId(id);
            return ResponseEntity.ok(agendamento);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<AgendamentosDTO>> listarPorSala(@PathVariable Integer salaId) {
        List<AgendamentosDTO> agendamentos = agendamentosService.listarPorSala(salaId);
        return ResponseEntity.ok(agendamentos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AgendamentosDTO> atualizar(@PathVariable Integer id, @RequestBody AgendamentosDTO dto) {
        AgendamentosDTO agendamentoAtualizado = agendamentosService.atualizarAgendamento(id, dto);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        agendamentosService.cancelarAgendamento(id);
        return ResponseEntity.noContent().build();
    }
}