package com.gleysson.flavio.gestao_atendimentos.config;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // CORREÇÃO AQUI: Use .isEmpty() ou .isPresent() do Optional
        if (usuarioRepository.findByUsername("admin").isEmpty()) { // Verifica se o Optional está vazio (usuário não existe)
            Usuario user = new Usuario();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole("ADMIN");
            user.setFullName("Administrador do Sistema");
            user.setDepartamento(Usuario.Departamento.TECNICO);
            user.setCargo(Usuario.Cargo.GESTOR);

            usuarioRepository.save(user);
            System.out.println("Usuário 'admin' criado com sucesso!");
        } else {
            System.out.println("Usuário 'admin' já existe. Nenhuma ação necessária.");
        }
    }
}