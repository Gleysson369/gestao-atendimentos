package com.gleysson.flavio.gestao_atendimentos.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime; // Adicionado para uso no Service

public class RelatorioFiltros {

    private String nomeCliente;
    private String empresaCliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim;

    private String canalAtendimento;
    private Long atendenteId;
    private String departamentoAtendente;
    private Boolean atendimentoTransferencia; // NOVO CAMPO
    private String protocoloAtendimento; // NOVO CAMPO
    private String sortBy = "dataHoraAtendimento";
    private String sortDir = "desc";

    // Getters e Setters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(String empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getCanalAtendimento() {
        return canalAtendimento;
    }

    public void setCanalAtendimento(String canalAtendimento) {
        this.canalAtendimento = canalAtendimento;
    }

    public Long getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(Long atendenteId) {
        this.atendenteId = atendenteId;
    }

    public String getDepartamentoAtendente() {
        return departamentoAtendente;
    }

    public void setDepartamentoAtendente(String departamentoAtendente) {
        this.departamentoAtendente = departamentoAtendente;
    }

    // NOVO: Getters e Setters para os novos campos
    public Boolean getAtendimentoTransferencia() {
        return atendimentoTransferencia;
    }

    public void setAtendimentoTransferencia(Boolean atendimentoTransferencia) {
        this.atendimentoTransferencia = atendimentoTransferencia;
    }

    public String getProtocoloAtendimento() {
        return protocoloAtendimento;
    }

    public void setProtocoloAtendimento(String protocoloAtendimento) {
        this.protocoloAtendimento = protocoloAtendimento;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}