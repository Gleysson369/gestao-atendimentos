package com.gleysson.flavio.gestao_atendimentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // busca o arquivo login.html em /templates
    }

    @GetMapping("/")
    public String home() {
        return "home"; // busca home.html em /templates
    }
}