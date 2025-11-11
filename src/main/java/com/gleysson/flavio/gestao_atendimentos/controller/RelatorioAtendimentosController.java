package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.dto.RelatorioFiltros;
import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/relatorio-atendimentos")
public class RelatorioAtendimentosController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String gerarRelatorio(
            @ModelAttribute("filtros") RelatorioFiltros filtros,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                                      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Usuario currentUser = null;
        if (!isAdmin) {
            Optional<Usuario> currentUserOpt = usuarioService.findByUsername(currentUsername);
            if (currentUserOpt.isPresent()) {
                currentUser = currentUserOpt.get();
            } else {
                model.addAttribute("errorMessage", "Erro: Usuário logado não encontrado no sistema.");
                return "relatorio-atendimentos";
            }
        }

        boolean filtrosAplicados =
                (filtros.getEmpresaCliente() != null && !filtros.getEmpresaCliente().trim().isEmpty()) ||
                (filtros.getNomeCliente() != null && !filtros.getNomeCliente().trim().isEmpty()) ||
                (filtros.getCanalAtendimento() != null && !filtros.getCanalAtendimento().trim().isEmpty()) ||
                (filtros.getAtendenteId() != null) ||
                (filtros.getDataInicio() != null) ||
                (filtros.getDataFim() != null) ||
                (filtros.getDepartamentoAtendente() != null && !filtros.getDepartamentoAtendente().trim().isEmpty()) ||
                (filtros.getAtendimentoTransferencia() != null) || // NOVO
                (filtros.getProtocoloAtendimento() != null && !filtros.getProtocoloAtendimento().trim().isEmpty()); // NOVO

        List<Atendimento> atendimentos;
        if (filtrosAplicados) {
            atendimentos = atendimentoService.buscarAtendimentosComFiltros(filtros, isAdmin, currentUser);
        } else {
            atendimentos = new ArrayList<>();
        }

        boolean atendimentosEncontrados = !atendimentos.isEmpty();
        if (filtrosAplicados && !atendimentosEncontrados) {
            model.addAttribute("errorMessage", "Nenhum dado encontrado com os filtros aplicados.");
        }
        
        model.addAttribute("atendimentos", atendimentos);

        model.addAttribute("canaisAtendimento", Atendimento.CanalAtendimento.values());
        model.addAttribute("todosUsuarios", usuarioService.buscarTodosUsuarios());
        model.addAttribute("todosDepartamentos", Usuario.Departamento.values());

        return "relatorio-atendimentos";
    }

    @ModelAttribute("filtros")
    public RelatorioFiltros initFiltros() {
        return new RelatorioFiltros();
    }
}