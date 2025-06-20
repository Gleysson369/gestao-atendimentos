package com.gleysson.flavio.gestao_atendimentos;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestaoAtendimentosApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(GestaoAtendimentosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername("admin") == null) {
            Usuario user = new Usuario();
            user.setUsername("admin");
            user.setPassword(encoder.encode("1234"));
            usuarioRepository.save(user);
            System.out.println("Usuário admin criado com sucesso!");
        }
    }
}