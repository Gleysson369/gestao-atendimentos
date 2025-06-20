package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // Import para @PreAuthorize
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cadastro-atendimento")
public class CadastroAtendimentosController {

    @Autowired
    private AtendimentoService atendimentoService;

    // Exibe o formulário de cadastro e a lista de atendimentos
    @GetMapping
    public String mostrarFormulario(Model model) {
        // Se não houver um 'atendimento' no modelo (ex: primeiro acesso ou após salvar), cria um novo
        if (!model.containsAttribute("atendimento")) {
            model.addAttribute("atendimento", new Atendimento());
        }
        // Adiciona a lista de todos os atendimentos para exibir na tabela
        model.addAttribute("atendimentos", atendimentoService.buscarTodosAtendimentos());
        return "cadastro-atendimento";
    }

    // Salva ou atualiza um atendimento
    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Ambos ADMIN e USER podem salvar
    public String salvar(@ModelAttribute Atendimento atendimento, RedirectAttributes redirectAttributes) {
        try {
            atendimentoService.salvarAtendimento(atendimento);
            redirectAttributes.addFlashAttribute("successMessage", "Atendimento salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar atendimento: " + e.getMessage());
            // Para manter o objeto 'atendimento' no formulário em caso de erro
            redirectAttributes.addFlashAttribute("atendimento", atendimento);
        }
        // Redireciona de volta para a página de cadastro de atendimentos
        return "redirect:/cadastro-atendimento";
    }

    // Carrega um atendimento para edição
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Ambos ADMIN e USER podem editar
    public String editarAtendimento(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Atendimento> atendimentoOpt = atendimentoService.buscarAtendimentoPorId(id);

        if (atendimentoOpt.isPresent()) {
            model.addAttribute("atendimento", atendimentoOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Atendimento não encontrado para edição.");
            return "redirect:/cadastro-atendimento";
        }
        // Recarrega a lista de atendimentos para exibir abaixo do formulário
        model.addAttribute("atendimentos", atendimentoService.buscarTodosAtendimentos());
        return "cadastro-atendimento";
    }

    // Deleta um atendimento
    @PostMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode deletar atendimentos
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