package com.gleysson.flavio.gestao_atendimentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empresaCliente; // Nome da empresa

    private String nomeCliente;

    private LocalDateTime dataHoraAtendimento;

    @Column(length = 1000)
    private String motivoContato;

    @Column(length = 1000)
    private String solucaoPassada;

    private String numeroCRS;

    private String telefone;

    private String anydesk;

    @Enumerated(EnumType.STRING)
    private CanalAtendimento canalAtendimento;

    // ENUM para canal de atendimento
    public enum CanalAtendimento {
        TELEFONE,
        MKON_WHATSAPP,
        EMAIL
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmpresaCliente() { return empresaCliente; }
    public void setEmpresaCliente(String empresaCliente) { this.empresaCliente = empresaCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public LocalDateTime getDataHoraAtendimento() { return dataHoraAtendimento; }
    public void setDataHoraAtendimento(LocalDateTime dataHoraAtendimento) { this.dataHoraAtendimento = dataHoraAtendimento; }

    public String getMotivoContato() { return motivoContato; }
    public void setMotivoContato(String motivoContato) { this.motivoContato = motivoContato; }

    public String getSolucaoPassada() { return solucaoPassada; }
    public void setSolucaoPassada(String solucaoPassada) { this.solucaoPassada = solucaoPassada; }

    public String getNumeroCRS() { return numeroCRS; }
    public void setNumeroCRS(String numeroCRS) { this.numeroCRS = numeroCRS; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getAnydesk() { return anydesk; }
    public void setAnydesk(String anydesk) { this.anydesk = anydesk; }

    public CanalAtendimento getCanalAtendimento() { return canalAtendimento; }
    public void setCanalAtendimento(CanalAtendimento canalAtendimento) { this.canalAtendimento = canalAtendimento; }
}