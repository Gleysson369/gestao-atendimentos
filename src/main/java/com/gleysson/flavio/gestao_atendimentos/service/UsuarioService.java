package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy; // Importar a anotação @Lazy
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional; // Importe Optional

@SuppressWarnings("unused")
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Lazy // Adicionar @Lazy aqui para resolver a dependência circular
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean redefinirSenha(String username, String newPassword) {
        // 1. Buscar o usuário pelo username
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            // Se o usuário não for encontrado, retornar false
            System.out.println("Usuário não encontrado para redefinição de senha: " + username);
            return false;
        }

        try {
            // 2. Criptografar a nova senha
            String encodedPassword = passwordEncoder.encode(newPassword);
            usuario.setPassword(encodedPassword);

            // 3. Salvar o usuário atualizado no banco de dados
            usuarioRepository.save(usuario);
            System.out.println("Senha redefinida com sucesso para o usuário: " + username);
            return true; // Senha redefinida com sucesso
        } catch (Exception e) {
            // Capturar qualquer exceção durante a criptografia ou salvamento
            System.err.println("Erro ao redefinir a senha para o usuário " + username + ": " + e.getMessage());
            e.printStackTrace(); // Imprimir o stack trace para depuração
            return false; // Ocorreu um erro
        }
    }

    // Você pode ter outros métodos de serviço aqui, como findByUsername, etc.
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}