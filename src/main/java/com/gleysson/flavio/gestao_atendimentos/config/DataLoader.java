package com.gleysson.flavio.gestao_atendimentos.config;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
<<<<<<< HEAD
import org.springframework.context.annotation.Lazy;
=======
import org.springframework.context.annotation.Lazy; // Importe esta anotação
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

<<<<<<< HEAD
=======
    // Adicione @Lazy aqui para que o PasswordEncoder seja injetado como um proxy
    // e o bean real só seja resolvido quando for realmente usado (no método run).
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509
    public DataLoader(UsuarioRepository usuarioRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
<<<<<<< HEAD
        // CORREÇÃO AQUI: Use .isEmpty() ou .isPresent() do Optional
        if (usuarioRepository.findByUsername("admin").isEmpty()) { // Verifica se o Optional está vazio (usuário não existe)
            Usuario user = new Usuario();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole("ADMIN");
            user.setFullName("Administrador do Sistema");
            user.setDepartamento(Usuario.Departamento.TECNICO);
            user.setCargo(Usuario.Cargo.GESTOR);
=======
        if (usuarioRepository.findByUsername("admin") == null) { // Usando findByUsername conforme seu UsuarioRepository
            Usuario user = new Usuario();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("1234")); // A senha "1234" será criptografada
            user.setRole("ADMIN"); // O papel "ADMIN"
            user.setFullName("Administrador do Sistema");
            user.setDepartamento(Usuario.Departamento.TECNICO); // Exemplo de atribuição
            user.setCargo(Usuario.Cargo.GESTOR); // Exemplo de atribuição
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509

            usuarioRepository.save(user);
            System.out.println("Usuário 'admin' criado com sucesso!");
        } else {
            System.out.println("Usuário 'admin' já existe. Nenhuma ação necessária.");
        }
    }
}