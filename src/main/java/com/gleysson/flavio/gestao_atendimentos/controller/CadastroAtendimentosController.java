package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.dto.RelatorioFiltros;
import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.service.AtendimentoService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cadastro-atendimento")
public class CadastroAtendimentosController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarFormulario(Model model,
                                    @RequestParam(name = "id", required = false) Long id) {

        Atendimento atendimento = new Atendimento();
        if (id != null) {
            atendimento = atendimentoService.buscarAtendimentoPorId(id).orElse(new Atendimento());
        } else {
            atendimento = new Atendimento();
            atendimento.setCanalAtendimento(Atendimento.CanalAtendimento.MKON_WHATSAPP);
        }

        RelatorioFiltros filtros = new RelatorioFiltros();
        List<Atendimento> atendimentos = new ArrayList<>(); // A lista começa vazia.

        model.addAttribute("atendimento", atendimento);
        model.addAttribute("atendimentos", atendimentos); // A lista vazia é enviada ao modelo.
        model.addAttribute("filtros", filtros);
        model.addAttribute("usuarios", usuarioService.buscarTodosUsuarios());

        return "cadastro-atendimento";
    }

    @GetMapping("/buscar")
    public String buscarAtendimentos(@ModelAttribute RelatorioFiltros filtros, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Usuario currentUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUser = usuarioService.findByUsername(userDetails.getUsername()).orElse(null);
        }

        List<Atendimento> atendimentos = atendimentoService.buscarAtendimentosComFiltros(filtros, isAdmin, currentUser);

        model.addAttribute("atendimento", new Atendimento());
        model.addAttribute("atendimentos", atendimentos); // Apenas aqui a lista é populada com os resultados da busca.
        model.addAttribute("filtros", filtros);
        model.addAttribute("usuarios", usuarioService.buscarTodosUsuarios());
        
        return "cadastro-atendimento";
    }

    @PostMapping("/salvar")
    public String salvarAtendimento(@ModelAttribute Atendimento atendimento, RedirectAttributes redirectAttributes) {
        try {
            atendimentoService.salvarAtendimento(atendimento);
            redirectAttributes.addFlashAttribute("successMessage", "Atendimento salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar atendimento: " + e.getMessage());
            redirectAttributes.addFlashAttribute("atendimento", atendimento);
        }
        return "redirect:/cadastro-atendimento";
    }

    @GetMapping("/editar/{id}")
    public String editarAtendimento(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        return "redirect:/cadastro-atendimento?id=" + id;
    }

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