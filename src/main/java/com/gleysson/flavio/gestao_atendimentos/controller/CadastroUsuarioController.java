package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // Importe esta anotação
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List; // Importe List
import java.util.Optional;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/cadastro-usuario")
@PreAuthorize("hasRole('ADMIN')") // <-- APENAS ADMIN PODE ACESSAR ESTE CONTROLLER
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

    // Salvar/Atualizar Usuário. Acesso apenas para ADMIN.
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (usuario.getId() == null) { // Novo usuário
                if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Nome de usuário já existe. Por favor, escolha outro.");
                    return "redirect:/cadastro-usuario";
                }
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            } else { // Edição de usuário existente
                Optional<Usuario> existingUserOptional = usuarioRepository.findById(usuario.getId());
                if (existingUserOptional.isPresent()) {
                    Usuario existingUser = existingUserOptional.get();
                    if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                        usuario.setPassword(existingUser.getPassword());
                    } else {
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
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace(); // Para depuração
        }
        return "redirect:/cadastro-usuario";
    }

    // Edição de Usuário (preencher formulário para edição). Acesso apenas para ADMIN.
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

    // Deletar Usuário. Acesso apenas para ADMIN.
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
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar usuário: " + e.getMessage());
        }
        return "redirect:/cadastro-usuario";
    }
}