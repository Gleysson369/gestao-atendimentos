package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // Mantenha o import se usar em outros métodos
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cadastro-atendimento")
public class CadastroAtendimentosController {

    @Autowired
    private AtendimentoService atendimentoService;

    // Exibe o formulário de cadastro e a lista de atendimentos.
    // Acesso permitido para qualquer usuário autenticado pela SecurityConfig.
    @GetMapping
    public String mostrarFormulario(Model model, @RequestParam(name = "id", required = false) Long id) {
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
        List<Atendimento> atendimentos = atendimentoService.buscarTodosAtendimentos();
        model.addAttribute("atendimentos", atendimentos);
        return "cadastro-atendimento";
    }

    // Salva ou atualiza um atendimento.
    // Acesso permitido para qualquer usuário autenticado pela SecurityConfig.
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Atendimento atendimento, RedirectAttributes redirectAttributes) {
        try {
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
    // REMOVIDO: @PreAuthorize("hasRole('ADMIN')")
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