package csi.sistema_agendamentos.controller;

import csi.sistema_agendamentos.dto.SalaDTO; // Importar o DTO
import csi.sistema_agendamentos.model.Sala;
import csi.sistema_agendamentos.service.SalaService;
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
    public ResponseEntity<SalaDTO> criar(@RequestBody SalaDTO salaDTO) {
        SalaDTO novaSalaDTO = salaService.salvar(salaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSalaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> atualizar(@PathVariable Integer id, @RequestBody SalaDTO salaDTO) {
        try {
            SalaDTO salaAtualizadaDTO = salaService.atualizar(id, salaDTO);
            return ResponseEntity.ok(salaAtualizadaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            salaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}