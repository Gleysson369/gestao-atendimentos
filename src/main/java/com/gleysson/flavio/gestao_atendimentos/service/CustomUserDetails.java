// src/main/java/com/gleysson/flavio/gestao_atendimentos/service/CustomUserDetails.java
package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
// import java.util.Base64; // REMOVIDO: Não precisamos mais do Base64

public class CustomUserDetails extends User {

    private final Long id;
    private final String fullName;
    // REMOVIDO: Campo para a foto em Base64
    // private final String photoBase64;

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getUsername(), usuario.getPassword(), authorities);
        this.id = usuario.getId();
        this.fullName = usuario.getFullName();
        // REMOVIDO: Lógica de conversão da foto para Base64
        // if (usuario.getPhoto() != null && usuario.getPhoto().length > 0) {
        //     this.photoBase64 = Base64.getEncoder().encodeToString(usuario.getPhoto());
        // } else {
        //     this.photoBase64 = null;
        // }
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    // REMOVIDO: Getter para photoBase64
    // public String getPhotoBase64() {
    //     return photoBase64;
    // }
}