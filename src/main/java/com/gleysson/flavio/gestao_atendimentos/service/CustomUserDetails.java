package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final Long id;
    private final String fullName;
    private final Usuario.Departamento departamento; 
    private final Usuario.Cargo cargo;           

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getUsername(), usuario.getPassword(), authorities);
        this.id = usuario.getId();
        this.fullName = usuario.getFullName();
        this.departamento = usuario.getDepartamento(); 
        this.cargo = usuario.getCargo();           
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Usuario.Departamento getDepartamento() { 
        return departamento;
    }

    public Usuario.Cargo getCargo() {          
        return cargo;
    }
}