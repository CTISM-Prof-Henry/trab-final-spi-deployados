package csi.sistema_agendamentos.service;

import csi.sistema_agendamentos.model.Usuario;
import csi.sistema_agendamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario); //save automatiza insert ou update
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer id) { //optional para tratar nulo
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Integer id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setCpf(usuarioAtualizado.getCpf());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setMatricula(usuarioAtualizado.getMatricula());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setTipoUsuario(usuarioAtualizado.getTipoUsuario());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public void deletarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
