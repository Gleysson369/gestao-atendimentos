package com.gleysson.flavio.gestao_atendimentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main") // ESTE DEVE PERMANECER AQUI
    public String mainPage(Model model) {
        // Mock dos dados para o dashboard
        model.addAttribute("atendimentosMensais", 32);
        model.addAttribute("atendimentosDiarios", 5);
        model.addAttribute("topClientes", new String[][] {
            {"Cliente A", "15"},
            {"Cliente B", "12"},
            {"Cliente C", "10"}
        });         
        model.addAttribute("topEmpresas", new String[][] {
            {"Empresa X", "20"},
            {"Empresa Y", "18"},
            {"Empresa Z", "14"}
        });
        return "main"; // busca o arquivo main.html em templates
    }
}