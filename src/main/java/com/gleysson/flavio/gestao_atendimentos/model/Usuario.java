package com.gleysson.flavio.gestao_atendimentos.model;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // ID de login, renomeado para 'username' para Spring Security

    @Column(nullable = false)
    private String fullName; // Nome completo

    @Column(nullable = false)
    private String password; // Senha (já será criptografada)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Departamento departamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    // A role do usuário pode ser derivada do Cargo ou ser um campo separado.
    // Se for um campo separado, mantenha como String e use-o para Spring Security.
    // Ex: "ROLE_ADMIN", "ROLE_ANALISTA".
    // Se for derivado do Cargo, você pode ter um método no Usuario que retorne a Role.
    // Por simplicidade, vou manter 'role' como String, como você tinha.
    @Column(nullable = false)
    private String role; // Papel do usuário (ex: ADMIN, ANALISTA)

    // Enums internos para Departamento e Cargo
    public enum Departamento {
        VENDAS("Vendas"),
        LOGISTICA("Logística"),
        TECNICO("Técnico"),
        FINANCEIRO("Financeiro"),
        FISCAL_EMISSAO("Fiscal (Emissão)"),
        FISCAL_APURACAO("Fiscal (Apuração)"),
        FISCAL_ENTRADAS("Fiscal (Entradas)"),
        COMPRAS("Compras"),
        OUTROS("Outros");

        private final String displayValue;

        Departamento(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum Cargo {
        ATENDENTE("Atendente"),
        ANALISTA("Analista"),
        SUPERVISOR("Supervisor"),
        GESTOR("Gestor");

        private final String displayValue;

        Cargo(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    // Construtor padrão
    public Usuario() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}