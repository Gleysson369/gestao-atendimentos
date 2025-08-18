package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List; // Importar List
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean redefinirSenha(String username, String newPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuário não encontrado para redefinição de senha: " + username);
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        try {
            String encodedPassword = passwordEncoder.encode(newPassword);
            usuario.setPassword(encodedPassword);
            usuarioRepository.save(usuario);
            System.out.println("Senha redefinida com sucesso para o usuário: " + username);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao redefinir a senha para o usuário " + username + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // NOVO MÉTODO: Retorna todos os usuários
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
}