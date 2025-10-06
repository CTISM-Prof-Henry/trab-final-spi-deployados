package csi.sistema_agendamentos.controller;

import csi.sistema_agendamentos.dto.LoginDTO;
import csi.sistema_agendamentos.model.Usuario;
import csi.sistema_agendamentos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login, HttpSession session) {
        if (login.getCpf() == null || login.getCpf().trim().isEmpty() ||
                login.getSenha() == null || login.getSenha().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("CPF e senha são obrigatórios.");
        }

        Usuario usuario = usuarioService.buscarUsuarioPorCpf(login.getCpf());

        if (usuario == null || !usuario.getSenha().equals(login.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF ou senha incorretos.");
        }


        session.setAttribute("idUsuario", usuario.getIdUsuario());
        session.setAttribute("tipoUsuario", usuario.getTipoUsuario());

        return ResponseEntity.ok(Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nome", usuario.getNome(),
                "tipoUsuario", usuario.getTipoUsuario()
        ));
    }
    @GetMapping("/acessar/{id}")
    public ResponseEntity<String> acessarRecurso(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return switch (usuario.getTipoUsuario()) {
            case 1 -> ResponseEntity.ok("Acessado: ADMIN.");
            case 2 -> ResponseEntity.ok("Acessado: DOCENTE.");
            case 3 -> ResponseEntity.ok("Acessado: ALUNO.");
            default -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        };
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
