package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.service.DashboardService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model,
                           @RequestParam(name = "periodo", defaultValue = "diario") String periodo) {

        Usuario usuarioLogado = null;
        if (userDetails != null) {
            Optional<Usuario> usuarioOptional = usuarioService.findByUsername(userDetails.getUsername());
            if (usuarioOptional.isPresent()) {
                usuarioLogado = usuarioOptional.get();
            }
        }

        Map<String, Object> dashboardData = dashboardService.getDashboardData(usuarioLogado, periodo);

        model.addAttribute("dadosDashboard", dashboardData);
        model.addAttribute("periodoSelecionado", periodo);

        // Adiciona todos os atributos de forma individual para manter a compatibilidade
        model.addAttribute("appVersion", dashboardData.get("appVersion"));
        model.addAttribute("atendimentosTotais", dashboardData.get("atendimentosTotais"));
        model.addAttribute("atendimentosMensais", dashboardData.get("atendimentosMensais"));
        model.addAttribute("atendimentosDiarios", dashboardData.get("atendimentosDiarios"));
        model.addAttribute("meusAtendimentos", dashboardData.get("meusAtendimentos"));
        model.addAttribute("meusAtendimentosDiarios", dashboardData.get("meusAtendimentosDiarios"));
        model.addAttribute("topClientes", dashboardData.get("topClientes"));
        model.addAttribute("topEmpresas", dashboardData.get("topEmpresas"));
        model.addAttribute("topAtendentes", dashboardData.get("topAtendentes"));
        model.addAttribute("atendimentosPorDepartamento", dashboardData.get("atendimentosPorDepartamento"));
        model.addAttribute("totalCrsMensal", dashboardData.get("totalCrsMensal"));
        model.addAttribute("totalCrsDiario", dashboardData.get("totalCrsDiario"));
        model.addAttribute("meusAtendimentosMensais", dashboardData.get("meusAtendimentosMensais"));
        model.addAttribute("metaAtendimentos", dashboardData.get("metaAtendimentos"));
        model.addAttribute("progressoMeta", dashboardData.get("progressoMeta"));
        model.addAttribute("mediaMovelData", dashboardData.get("mediaMovelData")); 

        return "main";
    }

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main";
    }

    @PostMapping("/main/salvarMeta")
    public String salvarMetaMensal(@RequestParam("novaMeta") Long novaMeta,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<Usuario> usuarioOptional = usuarioService.findByUsername(userDetails.getUsername());
            if (usuarioOptional.isPresent()) {
                Usuario usuarioLogado = usuarioOptional.get();
                dashboardService.salvarMetaMensal(usuarioLogado, novaMeta);
                usuarioService.salvarUsuario(usuarioLogado); // Salva o usuário com a nova meta
            }
        }
        return "redirect:/main";
    }
}