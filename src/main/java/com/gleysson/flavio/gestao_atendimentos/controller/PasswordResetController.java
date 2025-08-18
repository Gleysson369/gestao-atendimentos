package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Remova se não estiver usando o 'Model' no GET
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SuppressWarnings("unused")
@Controller
public class PasswordResetController {

    @Autowired
    private UsuarioService usuarioService;

    // Este @GetMapping("/redefinir-senha") deve ser o único para essa rota
    @GetMapping("/redefinir-senha")
    public String mostrarFormularioRedefinirSenha() {
        return "redefinir-senha"; // precisa do arquivo redefinir-senha.html na pasta templates
    }

    // POST para processar a redefinição de senha
    @PostMapping("/redefinir-senha")
    public String resetPassword(
            @RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes) {

        try {
            boolean passwordResetSuccess = usuarioService.redefinirSenha(username, newPassword);

            if (passwordResetSuccess) {
                redirectAttributes.addFlashAttribute("successMessage", "Senha redefinida com sucesso! Por favor, faça login com sua nova senha.");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "ID de Login não encontrado. Por favor, verifique e tente novamente.");
                return "redirect:/redefinir-senha";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao redefinir a senha. Tente novamente mais tarde.");
            return "redirect:/redefinir-senha";
        }
    }
}