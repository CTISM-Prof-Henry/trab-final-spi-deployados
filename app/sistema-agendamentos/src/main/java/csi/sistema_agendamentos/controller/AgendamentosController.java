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
    public ResponseEntity<?> criar(@RequestBody final AgendamentosDTO dto) {
        ResponseEntity<?> response;
        try {
            final AgendamentosDTO agendamentoCriado = agendamentosService.criarAgendamento(dto);
            response = new ResponseEntity<>(agendamentoCriado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }



    @GetMapping
    public ResponseEntity<List<AgendamentosDTO>> listarTodos() {
        final List<AgendamentosDTO> agendamentos = agendamentosService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable final Integer id) {
        ResponseEntity<?> response;
        try {
           final AgendamentosDTO agendamento = agendamentosService.buscarPorId(id);
            response = ResponseEntity.ok(agendamento);
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<AgendamentosDTO>> listarPorSala(@PathVariable final Integer salaId) {
        final List<AgendamentosDTO> agendamentos = agendamentosService.listarPorSala(salaId);
        return ResponseEntity.ok(agendamentos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AgendamentosDTO> atualizar(@PathVariable final Integer id, @RequestBody AgendamentosDTO dto) {
        final AgendamentosDTO agendamentoAtualizado = agendamentosService.atualizarAgendamento(id, dto);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable final Integer id) {
        agendamentosService.cancelarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AgendamentosDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<AgendamentosDTO> agendamentos = agendamentosService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(agendamentos);
    }

}