package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // Aqui está o segredo: ele pega o papel (ADMIN, Analista, etc.)
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole());
        return new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(authority));
    }
    public boolean redefinirSenha(String username, String novaSenha) {
    Usuario usuario = usuarioRepository.findByUsername(username);
    if (usuario != null) {
        usuario.setPassword(novaSenha); // De preferência, use BCrypt aqui
        usuarioRepository.save(usuario);
        return true;
    }
    return false;
}
}
