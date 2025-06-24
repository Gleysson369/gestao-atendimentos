package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario; // Importe o modelo Usuario
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService; // Importe o UsuarioService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Para obter informações do usuário logado
import org.springframework.security.core.context.SecurityContextHolder; // Para obter o contexto de segurança
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cadastro-atendimento")
public class CadastroAtendimentosController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired // Injete o UsuarioService AQUI!
    private UsuarioService usuarioService;

    // Exibe o formulário de cadastro e a lista de atendimentos.
    @GetMapping
    public String mostrarFormulario(Model model, @RequestParam(name = "id", required = false) Long id) {
        // Lógica para edição de atendimento existente
        if (id != null) {
            Optional<Atendimento> atendimentoExistente = atendimentoService.buscarAtendimentoPorId(id);
            if (atendimentoExistente.isPresent()) {
                model.addAttribute("atendimento", atendimentoExistente.get());
            } else {
                model.addAttribute("atendimento", new Atendimento());
                model.addAttribute("errorMessage", "Atendimento não encontrado para edição.");
            }
        } else {
            model.addAttribute("atendimento", new Atendimento());
        }

        // --- LÓGICA PARA FILTRAR ATENDIMENTOS POR USUÁRIO/ROLE ---
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Obtém o username do usuário logado

        List<Atendimento> atendimentos;

        // Verifica se o usuário logado tem a role "ADMIN"
        boolean isAdmin = authentication.getAuthorities().stream()
                                      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // Se for ADMIN, busca TODOS os atendimentos
            atendimentos = atendimentoService.buscarTodosAtendimentos();
        } else {
            // Se NÃO for ADMIN, busca o objeto Usuario do usuário logado
            Optional<Usuario> currentUserOpt = usuarioService.findByUsername(currentUsername); // CORREÇÃO: Chamar findByUsername do service que retorna Optional
            if (currentUserOpt.isPresent()) {
                Usuario currentUser = currentUserOpt.get();
                // E busca apenas os atendimentos cadastrados por ele
                atendimentos = atendimentoService.buscarAtendimentosPorUsuario(currentUser);
            } else {
                // Caso o usuário não seja encontrado (situação improvável se estiver autenticado)
                atendimentos = new ArrayList<>();
                model.addAttribute("errorMessage", "Erro: Usuário logado não encontrado no sistema.");
            }
        }
        model.addAttribute("atendimentos", atendimentos);
        // --- FIM DA LÓGICA DE FILTRAGEM ---

        return "cadastro-atendimento";
    }

    // O método salvar já foi ajustado no AtendimentoService para atribuir o UsuarioAtendente
    // Não precisa de modificação adicional aqui no controller para esse ponto.
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Atendimento atendimento, RedirectAttributes redirectAttributes) {
        try {
            // A lógica de associação do usuário logado ao atendimento está agora no AtendimentoService.salvarAtendimento
            atendimentoService.salvarAtendimento(atendimento);
            redirectAttributes.addFlashAttribute("successMessage", "Atendimento salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar atendimento: " + e.getMessage());
            redirectAttributes.addFlashAttribute("atendimento", atendimento);
        }
        return "redirect:/cadastro-atendimento";
    }

    // Carrega um atendimento para edição.
    // Acesso permitido para qualquer usuário autenticado pela SecurityConfig.
    @GetMapping("/editar/{id}")
    public String editarAtendimento(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        return "redirect:/cadastro-atendimento?id=" + id;
    }

    // Deleta um atendimento. AGORA QUALQUER USUÁRIO AUTENTICADO PODE DELETAR.
    @PostMapping("/deletar/{id}")
    public String deletarAtendimento(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            atendimentoService.deletarAtendimento(id);
            redirectAttributes.addFlashAttribute("successMessage", "Atendimento deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar atendimento: " + e.getMessage());
        }
        return "redirect:/cadastro-atendimento";
    }
}