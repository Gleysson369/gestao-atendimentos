package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional; 

@Controller
@RequestMapping("/cadastro-usuario")
@PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode acessar este controller
public class CadastroUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Exibe o formulário com dados vazios e a lista de usuários. Acesso apenas para ADMIN.
    @GetMapping
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        model.addAttribute("users", usuarioRepository.findAll());
        model.addAttribute("departamentos", Usuario.Departamento.values());
        model.addAttribute("cargos", Usuario.Cargo.values());
        return "cadastro-usuario";
    }

    // Salvar/Atualizar Usuário.
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                RedirectAttributes redirectAttributes) {
        try {
            if (usuario.getId() == null) { // Novo usuário
                // Usar isPresent() para verificar se o Optional contém um usuário
                if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Nome de usuário já existe. Por favor, escolha outro.");
                    return "redirect:/cadastro-usuario";
                }
                // Codifica a senha apenas para novos usuários ou se uma nova senha for fornecida
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            } else { // Edição de usuário existente
                Optional<Usuario> existingUserOptional = usuarioRepository.findById(usuario.getId());
                if (existingUserOptional.isPresent()) {
                    Usuario existingUser = existingUserOptional.get();
                    // Se a senha estiver vazia no formulário, mantém a senha existente
                    if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                        usuario.setPassword(existingUser.getPassword());
                    } else {
                        // Se uma nova senha for fornecida, codifica-a
                        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    }
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Usuário não encontrado para edição.");
                    return "redirect:/cadastro-usuario";
                }
            }

            usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário salvo com sucesso!");
        } catch (Exception e) {
            // exibir uma mensagem genérica para o usuário
            System.err.println("Erro ao salvar usuário: " + e.getMessage()); // Log no console do servidor
            e.printStackTrace(); // Para depuração mais detalhada
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar usuário. Por favor, tente novamente.");
        }
        return "redirect:/cadastro-usuario";
    }

    // Edição de Usuário (preencher formulário para edição).
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("usuario", usuario);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Usuário não encontrado para edição.");
            return "redirect:/cadastro-usuario";
        }
        model.addAttribute("users", usuarioRepository.findAll());
        model.addAttribute("departamentos", Usuario.Departamento.values());
        model.addAttribute("cargos", Usuario.Cargo.values());
        return "cadastro-usuario";
    }

    // Deletar Usuário.
    @PostMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Verifica se o usuário logado está tentando deletar a si mesmo (opcional, mas boa prática)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            Optional<Usuario> usuarioToDelete = usuarioRepository.findById(id);

            if (usuarioToDelete.isPresent() && usuarioToDelete.get().getUsername().equals(currentUsername)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Você não pode deletar seu próprio usuário!");
                return "redirect:/cadastro-usuario";
            }

            usuarioRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário deletado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage()); // Log no console do servidor
            e.printStackTrace(); // Para depuração mais detalhada
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar usuário. Por favor, tente novamente.");
        }
        return "redirect:/cadastro-usuario";
    }
}