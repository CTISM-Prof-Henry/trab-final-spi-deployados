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


    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    public Optional<Usuario> buscarUsuarioPorId(final Integer id) {
        return usuarioRepository.findById(id);
    }


    public Usuario buscarUsuarioPorCpf(final String cpf) {
        return (Usuario) usuarioRepository.findByCpf(cpf).orElse(null);
    }

}
