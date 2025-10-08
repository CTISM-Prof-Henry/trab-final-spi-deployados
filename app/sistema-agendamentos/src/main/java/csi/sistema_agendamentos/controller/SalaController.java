package csi.sistema_agendamentos.controller;

import csi.sistema_agendamentos.dto.SalaDTO;
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.service.SalaService;
import jakarta.servlet.http.HttpSession; // 1. Importe o HttpSession
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    private boolean isAdmin(HttpSession session) {
        Integer tipoUsuario = (Integer) session.getAttribute("tipoUsuario");
        return tipoUsuario != null && tipoUsuario == 1;
    }

    @GetMapping
    public List<SalaDTO> listarTodas() {
        return salaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable Integer id) {
        return salaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/bloco/{bloco}")
    public ResponseEntity<List<Sala>> listarSalasPorBloco(@PathVariable String bloco) {
        List<Sala> salas = salaService.listarSalasPorBloco(bloco);
        return ResponseEntity.ok(salas);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody SalaDTO salaDTO, HttpSession session) {
        ResponseEntity<?> response;

        if (!isAdmin(session)) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado. Requer permissão de administrador.");
        }  else{
            SalaDTO novaSalaDTO = salaService.salvar(salaDTO);
            response = ResponseEntity.status(HttpStatus.CREATED).body(novaSalaDTO);

        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody SalaDTO salaDTO, HttpSession session) {
        ResponseEntity<?> response;
        if (!isAdmin(session)) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado. Requer permissão de administrador.");
        } else {
            try {
                SalaDTO salaAtualizadaDTO = salaService.atualizar(id, salaDTO);
                response = ResponseEntity.ok(salaAtualizadaDTO);
            } catch (RuntimeException e) {
                response = ResponseEntity.notFound().build();
            }
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id, HttpSession session) {
        ResponseEntity<String> response;
        if (!isAdmin(session)) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado. Requer permissão de administrador.");
        } else{
            try {
                salaService.deletar(id);
                response = ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                response = ResponseEntity.notFound().build();
            }
        }
        return response;
    }

}
