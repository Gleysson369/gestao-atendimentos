package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadastro-atendimento")
public class CadastroAtendimentosController {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("atendimento", new Atendimento());
        return "cadastro-atendimento"; // Nome do HTML
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Atendimento atendimento) {
        atendimentoRepository.save(atendimento);
        return "redirect:/main";
    }
}