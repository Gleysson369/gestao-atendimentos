package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Value("${app.version}")
    private String appVersion;

    public Map<String, Object> getDashboardData(Usuario usuarioLogado, String periodo) {
        Map<String, Object> data = new HashMap<>();

        LocalDateTime dataInicialSemana = LocalDateTime.now().minusDays(7);

        // Adiciona a contagem total de atendimentos
        data.put("atendimentosTotais", atendimentoRepository.countTotalAtendimentos());
        data.put("atendimentosMensais", atendimentoRepository.countMonthlyAtendimentos());
        data.put("atendimentosDiarios", atendimentoRepository.countDailyAtendimentos());
        data.put("atendimentosSemanais", atendimentoRepository.countWeeklyAtendimentos(dataInicialSemana));

        data.put("topClientes", convertObjectArrayToMap(getTopClientes(periodo, dataInicialSemana), "cliente"));
        data.put("topEmpresas", convertObjectArrayToMap(getTopEmpresas(periodo, dataInicialSemana), "empresa"));
        data.put("topAtendentes", convertObjectArrayToMap(getTopAtendentes(periodo, dataInicialSemana), "nome"));
        data.put("atendimentosPorDepartamento", convertObjectArrayToMap(getAtendimentosPorDepartamento(periodo, dataInicialSemana), "departamento"));

        // Total CRS
        data.put("totalCrsMensal", atendimentoRepository.countMonthlyCrs());
        data.put("totalCrsDiario", atendimentoRepository.countDailyCrs());

        if (usuarioLogado != null) {
            data.put("meusAtendimentos", atendimentoRepository.countByUsuarioAtendente(usuarioLogado));
            long meusAtendimentosMensais = atendimentoRepository.countMonthlyByUsuarioAtendente(usuarioLogado);
            data.put("meusAtendimentosMensais", meusAtendimentosMensais);
            data.put("meusAtendimentosDiarios", atendimentoRepository.countDailyByUsuarioAtendente(usuarioLogado));
            data.put("meusAtendimentosSemanais", atendimentoRepository.countWeeklyByUsuarioAtendente(usuarioLogado, dataInicialSemana));

            // Pega a meta do usuário. Se não houver, usa 300L como padrão
            long metaAtendimentos = (usuarioLogado.getMetaMensal() != null && usuarioLogado.getMetaMensal() > 0) ? usuarioLogado.getMetaMensal() : 300L;
            data.put("metaAtendimentos", metaAtendimentos);

            // NOVO CÁLCULO: Porcentagem da meta atingida
            double progressoMeta = 0.0;
            if (metaAtendimentos > 0) {
                progressoMeta = ((double) meusAtendimentosMensais / metaAtendimentos) * 100;
            }
            data.put("progressoMeta", progressoMeta);

        } else {
            data.put("meusAtendimentos", 0L);
            data.put("meusAtendimentosDiarios", 0L);
            data.put("meusAtendimentosSemanais", 0L);
            data.put("meusAtendimentosMensais", 0L);
            data.put("metaAtendimentos", 0L);
            data.put("progressoMeta", 0.0);
        }

        // Adiciona a versão do aplicativo
        data.put("appVersion", this.appVersion);

        // Adiciona os dados para o gráfico de média móvel
        data.put("mediaMovelData", getMediaMovelData());

        return data;
    }

    // NOVO MÉTODO PRIVADO PARA OBTER OS DADOS DE MÉDIA MÓVEL
    private List<Object[]> getMediaMovelData() {
        LocalDateTime seisMesesAtras = LocalDateTime.now().minusMonths(6);
        return atendimentoRepository.countMonthlyAtendimentosLastSixMonths(seisMesesAtras);
    }

    // NOVO MÉTODO para salvar a meta por usuário
    public void salvarMetaMensal(Usuario usuario, Long novaMeta) {
        if (usuario != null && novaMeta != null && novaMeta > 0) {
            usuario.setMetaMensal(novaMeta);
        }
    }

    private List<Object[]> getAtendimentosPorDepartamento(String periodo, LocalDateTime dataInicialSemana) {
        if ("semanal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.countAtendimentosPerDepartamentoSemanal(dataInicialSemana);
        } else if ("mensal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.countAtendimentosPerDepartamentoMensal();
        } else if ("anual".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.countAtendimentosPerDepartamentoAnual();
        } else if ("diario".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.countAtendimentosPerDepartamentoDiario();
        } else {
            return atendimentoRepository.countAtendimentosPerDepartamentoTotal();
        }
    }

    private List<Object[]> getTopClientes(String periodo, LocalDateTime dataInicialSemana) {
        if ("semanal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10ClientesAtendidosSemanal(dataInicialSemana);
        } else if ("mensal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10ClientesAtendidosMensal();
        } else if ("anual".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10ClientesAtendidosAnual();
        } else if ("diario".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10ClientesAtendidosDiario();
        } else {
            return atendimentoRepository.findTop10ClientesAtendidosTotal();
        }
    }

    private List<Object[]> getTopEmpresas(String periodo, LocalDateTime dataInicialSemana) {
        if ("semanal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10EmpresasAtendidasSemanal(dataInicialSemana);
        } else if ("mensal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10EmpresasAtendidasMensal();
        } else if ("anual".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10EmpresasAtendidasAnual();
        } else if ("diario".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10EmpresasAtendidasDiario();
        } else {
            return atendimentoRepository.findTop10EmpresasAtendidasTotal();
        }
    }

    private List<Object[]> getTopAtendentes(String periodo, LocalDateTime dataInicialSemana) {
        if ("semanal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10AtendentesSemanal(dataInicialSemana);
        } else if ("mensal".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10AtendentesMensal();
        } else if ("anual".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10AtendentesAnual();
        } else if ("diario".equalsIgnoreCase(periodo)) {
            return atendimentoRepository.findTop10AtendentesDiario();
        } else {
            return atendimentoRepository.findTop10AtendentesTotal();
        }
    }

    private List<Map<String, Object>> convertObjectArrayToMap(List<Object[]> data, String labelKey) {
        return data.stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put(labelKey, item[0]);
                    map.put("quantidade", (Long) item[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }
}