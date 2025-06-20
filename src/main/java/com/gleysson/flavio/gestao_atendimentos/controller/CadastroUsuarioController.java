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
// import org.springframework.web.multipart.MultipartFile; // REMOVIDO: Não precisamos mais do MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

// import java.io.IOException; // REMOVIDO: Não precisamos mais de IOException se não processar arquivos
import java.util.Optional;
// import java.util.Base64; // REMOVIDO: Não precisamos mais de Base64 se não processar fotos

@SuppressWarnings("unused")
@Controller
@RequestMapping("/cadastro-usuario")
public class CadastroUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Exibe o formulário com dados vazios e a lista de usuários
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

    // Salvar/Atualizar Usuário
    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode salvar/editar usuários
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                // @RequestParam("photo") MultipartFile file, // REMOVIDO: Não recebemos mais a foto
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
                    // Manter a senha existente se uma nova não for fornecida (formulário pode enviar campo vazio)
                    if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                        usuario.setPassword(existingUser.getPassword());
                    } else {
                        // Se uma nova senha for fornecida, criptografá-la
                        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    }
                    // REMOVIDO: Não precisamos mais manter a foto existente
                    // if (file.isEmpty()) {
                    //     usuario.setPhoto(existingUser.getPhoto());
                    // }
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


    // Edição de Usuário (preencher formulário para edição)
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode editar usuários
    public String editarUsuario(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            model.addAttribute("usuario", usuario); // Adiciona o objeto Usuario ao modelo
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Usuário não encontrado para edição.");
            return "redirect:/cadastro-usuario";
        }
        model.addAttribute("users", usuarioRepository.findAll()); // Recarrega a lista de usuários para exibir abaixo do formulário
        model.addAttribute("departamentos", Usuario.Departamento.values());
        model.addAttribute("cargos", Usuario.Cargo.values());
        return "cadastro-usuario";
    }

    // Deletar Usuário
    @PostMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode deletar usuários
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

    // Método para carregar a imagem do usuário (opcional, se você quiser exibir a foto do usuário logado)
    @ModelAttribute("currentUser")
    public Usuario getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            // AQUI VOCÊ DEVE GARANTIR QUE O MÉTODO findByUsername RETORNE UM USUÁRIO SEM O CAMPO DE FOTO OU COM ELE NULO
            // CASO CONTRÁRIO, VOCÊ AINDA PODE TER PROBLEMAS SE O OBJETO USUARIO TIVER UM CAMPO 'photoBase64' QUE ESTÁ SENDO ACESSADO
            // NO main.html (que causou o erro inicial)
            return usuarioRepository.findByUsername(username);
        }
        return null;
    }
}