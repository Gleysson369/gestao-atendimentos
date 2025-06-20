package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/relatorio-atendimentos") // Mapeamento correto da URL
public class RelatorioController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping
    public String gerarRelatorio(
            @RequestParam(value = "nomeCliente", required = false) String nomeCliente,
            @RequestParam(value = "empresaCliente", required = false) String empresaCliente,
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(value = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(value = "canalAtendimento", required = false) String canalAtendimento,
            @RequestParam(value = "sortBy", required = false, defaultValue = "dataHoraAtendimento") String sortBy, // Campo para ordenação
            @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir, // Direção da ordenação (asc/desc)
            Model model) {

        List<Atendimento> atendimentos = atendimentoService.buscarTodosAtendimentos(); // Começa com todos

        // --- Aplicação de Filtros ---
        if (nomeCliente != null && !nomeCliente.isEmpty()) {
            atendimentos = atendimentos.stream()
                    .filter(a -> a.getNomeCliente() != null && a.getNomeCliente().toLowerCase().contains(nomeCliente.toLowerCase()))
                    .toList();
        }

        if (empresaCliente != null && !empresaCliente.isEmpty()) {
            atendimentos = atendimentos.stream()
                    .filter(a -> a.getEmpresaCliente() != null && a.getEmpresaCliente().toLowerCase().contains(empresaCliente.toLowerCase()))
                    .toList();
        }

        if (dataInicio != null || dataFim != null) {
            // Se dataFim não for fornecida, assume-se a data e hora atual como fim do período
            LocalDateTime fimPeriodo = (dataFim != null) ? dataFim : LocalDateTime.now();
            // Se dataInicio não for fornecida, assume-se uma data muito antiga como início
            LocalDateTime inicioPeriodo = (dataInicio != null) ? dataInicio : LocalDateTime.MIN;

            // Filtra pela faixa de datas
            atendimentos = atendimentos.stream()
                    .filter(a -> a.getDataHoraAtendimento() != null &&
                            !a.getDataHoraAtendimento().isBefore(inicioPeriodo) &&
                            !a.getDataHoraAtendimento().isAfter(fimPeriodo))
                    .toList();
        }

        if (canalAtendimento != null && !canalAtendimento.isEmpty()) {
            try {
                Atendimento.CanalAtendimento canalEnum = Atendimento.CanalAtendimento.valueOf(canalAtendimento.toUpperCase());
                atendimentos = atendimentos.stream()
                        .filter(a -> a.getCanalAtendimento() == canalEnum)
                        .toList();
            } catch (IllegalArgumentException e) {
                model.addAttribute("errorMessage", "Canal de atendimento inválido: " + canalAtendimento);
            }
        }


        // --- Aplicação de Ordenação ---
        Comparator<Atendimento> comparator = null;
        if ("nomeCliente".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Atendimento::getNomeCliente, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        } else if ("dataHoraAtendimento".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Atendimento::getDataHoraAtendimento, Comparator.nullsLast(LocalDateTime::compareTo));
        }
        // Adicione outras opções de ordenação aqui, se necessário (ex: por Empresa)

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(sortDir)) {
                atendimentos.sort(comparator.reversed());
            } else {
                atendimentos.sort(comparator);
            }
        }


        model.addAttribute("atendimentos", atendimentos);
        model.addAttribute("filtros", new RelatorioFiltros(nomeCliente, empresaCliente, dataInicio, dataFim, canalAtendimento, sortBy, sortDir));
        model.addAttribute("canaisAtendimento", Atendimento.CanalAtendimento.values()); // Para o dropdown de canal

        return "relatorio"; // Nome do arquivo HTML
    }

    // Classe auxiliar para manter os valores dos filtros no formulário após a submissão
    // Isso é importante para que os campos de filtro não fiquem vazios ao recarregar a página
    public static class RelatorioFiltros {
        private String nomeCliente;
        private String empresaCliente;
        private LocalDateTime dataInicio;
        private LocalDateTime dataFim;
        private String canalAtendimento;
        private String sortBy;
        private String sortDir;

        public RelatorioFiltros(String nomeCliente, String empresaCliente, LocalDateTime dataInicio, LocalDateTime dataFim, String canalAtendimento, String sortBy, String sortDir) {
            this.nomeCliente = nomeCliente;
            this.empresaCliente = empresaCliente;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.canalAtendimento = canalAtendimento;
            this.sortBy = sortBy;
            this.sortDir = sortDir;
        }

        // Getters
        public String getNomeCliente() { return nomeCliente; }
        public String getEmpresaCliente() { return empresaCliente; }
        public LocalDateTime getDataInicio() { return dataInicio; }
        public LocalDateTime getDataFim() { return dataFim; }
        public String getCanalAtendimento() { return canalAtendimento; }
        public String getSortBy() { return sortBy; }
        public String getSortDir() { return sortDir; }

        // Setters (se precisar para binding, mas para @RequestParam não é estritamente necessário se usar o construtor)
        public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
        public void setEmpresaCliente(String empresaCliente) { this.empresaCliente = empresaCliente; }
        public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
        public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
        public void setCanalAtendimento(String canalAtendimento) { this.canalAtendimento = canalAtendimento; }
        public void setSortBy(String sortBy) { this.sortBy = sortBy; }
        public void setSortDir(String sortDir) { this.sortDir = sortDir; }
    }
}