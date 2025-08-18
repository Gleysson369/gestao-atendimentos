package com.gleysson.flavio.gestao_atendimentos.controller;

<<<<<<< HEAD
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.service.DashboardService;
import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509

@Controller
public class MainController {

<<<<<<< HEAD
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

        model.addAttribute("appVersion", dashboardData.get("appVersion"));
        
        // Adiciona o novo dado para o total de atendimentos
        model.addAttribute("atendimentosTotais", dashboardData.get("atendimentosTotais"));

        model.addAttribute("atendimentosMensais", dashboardData.get("atendimentosMensais"));
        model.addAttribute("atendimentosDiarios", dashboardData.get("atendimentosDiarios"));
        model.addAttribute("meusAtendimentos", dashboardData.get("meusAtendimentos"));
        model.addAttribute("meusAtendimentosDiarios", dashboardData.get("meusAtendimentosDiarios"));
        model.addAttribute("topClientes", dashboardData.get("topClientes"));
        model.addAttribute("topEmpresas", dashboardData.get("topEmpresas"));
        model.addAttribute("topAtendentes", dashboardData.get("topAtendentes"));
        model.addAttribute("atendimentosPorDepartamento", dashboardData.get("atendimentosPorDepartamento"));
        model.addAttribute("periodoSelecionado", periodo);

        return "main";
    }

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main";
=======
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
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509
    }
}