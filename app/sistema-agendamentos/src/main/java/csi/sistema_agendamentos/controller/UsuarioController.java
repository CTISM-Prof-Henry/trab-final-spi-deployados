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
    public ResponseEntity<?> login(@RequestBody final LoginDTO login, final HttpSession session) {
        ResponseEntity<?> response;

        if (login.getCpf() == null || login.getCpf().trim().isEmpty() ||
                login.getSenha() == null || login.getSenha().trim().isEmpty()) {
            response = ResponseEntity.badRequest().body("CPF e senha são obrigatórios.");
        } else {
            Usuario usuario = usuarioService.buscarUsuarioPorCpf(login.getCpf());

            if (usuario == null || !usuario.getSenha().equals(login.getSenha())) {
                response = ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("CPF ou senha incorretos.");
            } else {
                session.setAttribute("idUsuario", usuario.getIdUsuario());
                session.setAttribute("tipoUsuario", usuario.getTipoUsuario());

                response = ResponseEntity.ok(Map.of(
                        "idUsuario", usuario.getIdUsuario(),
                        "nome", usuario.getNome(),
                        "tipoUsuario", usuario.getTipoUsuario()
                ));
            }
        }

        return response;
    }

    @GetMapping("/acessar/{id}")
    public ResponseEntity<String> acessarRecurso(@PathVariable final Integer id) {
        ResponseEntity<String> response;

        final Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);

        if (usuario == null) {
            response = ResponseEntity.notFound().build();
        } else{
            switch (usuario.getTipoUsuario()) {
                case 1 -> response = ResponseEntity.ok("Acessado: ADMIN.");
                case 2 -> response = ResponseEntity.ok("Acessado: DOCENTE.");
                case 3 -> response = ResponseEntity.ok("Acessado: ALUNO.");
                default -> response  = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
            }
        }
        return response;

    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable final Integer id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
