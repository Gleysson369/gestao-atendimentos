package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.dto.RelatorioFiltros; // Importe o DTO
import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario; // Importe Usuario
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService; // Importe UsuarioService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Mantenha, mas o @ModelAttribute fará o binding

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/relatorio-atendimentos") // Mapeamento correto da URL
public class RelatorioAtendimentosController { // Classe renomeada para consistência

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String gerarRelatorio(
            @ModelAttribute("filtros") RelatorioFiltros filtros, // Recebe os filtros do formulário
            Model model) {

        // --- Lógica de visibilidade (ADMIN vê tudo, USER vê os seus) ---
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
        // --- Fim da lógica de visibilidade ---

        List<Atendimento> atendimentos = atendimentoService.buscarAtendimentosComFiltros(filtros, isAdmin, currentUser);
        model.addAttribute("atendimentos", atendimentos);

        // Adiciona a lista de todos os canais de atendimento para o filtro
        model.addAttribute("canaisAtendimento", Atendimento.CanalAtendimento.values());

        // NOVO: Adiciona a lista de todos os usuários para o filtro 'Atendente'
        model.addAttribute("todosUsuarios", usuarioService.buscarTodosUsuarios());

        // NOVO: Adiciona a lista de todos os departamentos para o filtro 'Departamento'
        model.addAttribute("todosDepartamentos", Usuario.Departamento.values());

        return "relatorio-atendimentos"; // Nome do arquivo HTML
    }

    // Método para inicializar o DTO de filtros no modelo se não houver um
    @ModelAttribute("filtros")
    public RelatorioFiltros initFiltros() {
        return new RelatorioFiltros();
    }
}