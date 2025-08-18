package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario; // Importa a classe Usuario
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final Long id;
    private final String fullName;
    private final Usuario.Departamento departamento; // Adicione este campo
    private final Usuario.Cargo cargo;           // Adicione este campo

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getUsername(), usuario.getPassword(), authorities);
        this.id = usuario.getId();
        this.fullName = usuario.getFullName();
        this.departamento = usuario.getDepartamento(); // Atribua o departamento
        this.cargo = usuario.getCargo();           // Atribua o cargo
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Usuario.Departamento getDepartamento() { // Adicione o getter para departamento
        return departamento;
    }

    public Usuario.Cargo getCargo() {           // Adicione o getter para cargo
        return cargo;
    }
}