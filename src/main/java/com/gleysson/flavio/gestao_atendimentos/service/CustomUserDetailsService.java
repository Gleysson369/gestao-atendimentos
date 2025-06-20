package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            System.out.println("DEBUG: Usuário '" + username + "' não encontrado no banco de dados.");
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRole()));

        System.out.println("DEBUG: Usuário '" + username + "' encontrado. Role(s): " + authorities);

        // Retorne sua CustomUserDetails, passando o objeto Usuario completo
        return new CustomUserDetails(
                usuario, // Passa o objeto Usuario completo
                authorities
        );
    }
}