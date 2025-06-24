package com.gleysson.flavio.gestao_atendimentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importe RedirectAttributes

@Controller
public class AcessoNegadoController {

    @GetMapping("/acesso-negado")
    public String acessoNegado(Model model, RedirectAttributes ra) {
        // Verifica se já existe uma mensagem de erro vinda de outro lugar
        // Se não houver, adiciona uma mensagem padrão
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "Você não tem permissão para acessar esta página ou realizar esta ação.");
        }
        return "acesso-negado"; // Nome do seu arquivo HTML (acesso-negado.html)
    }
}