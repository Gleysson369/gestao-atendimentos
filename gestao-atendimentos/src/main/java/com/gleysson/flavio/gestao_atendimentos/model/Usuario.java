package com.gleysson.flavio.gestao_atendimentos.model;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    
    // Getters e Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }
    public void setFoto(Object bytes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFoto'");
    }
    public void setRole(String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRole'");
    }
    public Object getFullName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFullName'");
    }
    public void setFullName(Object fullName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFullName'");
    }
    public Object getDepartment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartment'");
    }
    public void setDepartment(Object department) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDepartment'");
    }
    public Object getFoto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFoto'");
    }
}