package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.service.UsuarioService; // Presumindo que você tem um serviço para manipular usuários
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensagens de sucesso/erro após redirecionamento

@SuppressWarnings("unused")
@Controller
public class PasswordResetController {

    @Autowired
    private UsuarioService usuarioService; // Injete seu serviço de usuário aqui

    // GET para exibir a tela de redefinição de senha
    @GetMapping("/redefinir-senha")
    public String showResetPasswordForm() {
        return "redefinir-senha"; // Retorna o nome do template HTML
    }

    // POST para processar a redefinição de senha
    @PostMapping("/redefinir-senha")
    public String resetPassword(
            @RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes) {

        try {
            // Lógica para redefinir a senha
            // Aqui você chamaria um método no seu UsuarioService
            boolean passwordResetSuccess = usuarioService.redefinirSenha(username, newPassword);

            if (passwordResetSuccess) {
                // Adiciona mensagem de sucesso para ser exibida na tela de login
                redirectAttributes.addFlashAttribute("successMessage", "Senha redefinida com sucesso! Por favor, faça login com sua nova senha.");
                return "redirect:/login"; // Redireciona de volta para a tela de login
            } else {
                // Adiciona mensagem de erro para ser exibida na mesma tela de redefinição
                redirectAttributes.addFlashAttribute("errorMessage", "ID de Login não encontrado. Por favor, verifique e tente novamente.");
                return "redirect:/redefinir-senha"; // Permanece na tela de redefinição
            }
        } catch (Exception e) {
            // Captura qualquer outra exceção e exibe uma mensagem genérica de erro
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao redefinir a senha. Tente novamente mais tarde.");
            return "redirect:/redefinir-senha";
        }
    }
}