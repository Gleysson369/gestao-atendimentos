package com.gleysson.flavio.gestao_atendimentos.dto;

import java.time.LocalDate; // Mude de LocalDateTime para LocalDate
import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioFiltros {
    private String nomeCliente;
    private String empresaCliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicio; // Mude aqui

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim; // Mude aqui

    private String canalAtendimento;
    private Long atendenteId;
    private String departamentoAtendente;

    private String sortBy = "dataHoraAtendimento";
    private String sortDir = "desc";

    // Getters e Setters
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getEmpresaCliente() { return empresaCliente; }
    public void setEmpresaCliente(String empresaCliente) { this.empresaCliente = empresaCliente; }

    public LocalDate getDataInicio() { return dataInicio; } // Mude aqui
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; } // Mude aqui

    public LocalDate getDataFim() { return dataFim; } // Mude aqui
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; } // Mude aqui

    // ... (restante dos getters e setters)
    public String getCanalAtendimento() { return canalAtendimento; }
    public void setCanalAtendimento(String canalAtendimento) { this.canalAtendimento = canalAtendimento; }
    public Long getAtendenteId() { return atendenteId; }
    public void setAtendenteId(Long atendenteId) { this.atendenteId = atendenteId; }
    public String getDepartamentoAtendente() { return departamentoAtendente; }
    public void setDepartamentoAtendente(String departamentoAtendente) { this.departamentoAtendente = departamentoAtendente; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public String getSortDir() { return sortDir; }
    public void setSortDir(String sortDir) { this.sortDir = sortDir; }
}