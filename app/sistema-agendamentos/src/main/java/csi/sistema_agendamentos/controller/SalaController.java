package csi.sistema_agendamentos.controller;
import csi.sistema_agendamentos.service.SalaService;
import csi.sistema_agendamentos.model.Sala;
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

    // GET /salas
    @GetMapping
    public List<Sala> listarTodas() {
        return salaService.listarTodas();
    }

    // GET /salas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Integer id) {
        return salaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /salas
    @PostMapping
    public ResponseEntity<Sala> criar(@RequestBody Sala sala) {
        Sala novaSala = salaService.salvar(sala);
        return ResponseEntity.ok(novaSala);
    }

    // PUT /salas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Sala> atualizar(@PathVariable Integer id, @RequestBody Sala sala) {
        try {
            Sala salaAtualizada = salaService.atualizar(id, sala);
            return ResponseEntity.ok(salaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /salas/{id}
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
