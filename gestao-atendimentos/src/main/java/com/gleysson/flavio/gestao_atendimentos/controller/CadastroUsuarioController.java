package com.gleysson.flavio.gestao_atendimentos.controller;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("users", usuarioRepository.findAll());
        return "cadastro-usuario";
    }

    // Salva novo usuário
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                @RequestParam("fotoUsuario") MultipartFile fotoUsuario,
                                @RequestParam(required = false) String confirmPassword,
                                Model model) {

        if (usuario.getId() == null && !usuario.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMessage", "As senhas não coincidem.");
        } else {
            try {
                if (!fotoUsuario.isEmpty()) {
                    usuario.setFoto(fotoUsuario.getBytes());
                }
                if (usuario.getId() == null) {
                    // Novo cadastro
                    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    usuarioRepository.save(usuario);
                    model.addAttribute("successMessage", "Cadastro salvo com sucesso!");
                } else {
                    // Alteração
                    Optional<Usuario> existente = usuarioRepository.findById(usuario.getId());
                    if (existente.isPresent()) {
                        Usuario usuarioExistente = existente.get();
                        usuarioExistente.setUsername(usuario.getUsername());
                        usuarioExistente.setFullName(usuario.getFullName());
                        usuarioExistente.setDepartment(usuario.getDepartment());
                        usuarioExistente.setRole(usuario.getRole());
                        if (!usuario.getPassword().isBlank()) {
                            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
                        }
                        if (usuario.getFoto() != null) {
                            usuarioExistente.setFoto(usuario.getFoto());
                        }
                        usuarioRepository.save(usuarioExistente);
                        model.addAttribute("successMessage", "Cadastro alterado com sucesso!");
                    }
                }
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Erro ao processar a imagem.");
            }
        }

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("users", usuarioRepository.findAll());
        return "cadastro-usuario";
    }

    // Apenas ADMIN pode deletar
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deletar")
    public String deletarUsuario(@RequestParam("id") Long id, Model model) {
        usuarioRepository.deleteById(id);
        model.addAttribute("successMessage", "Cadastro deletado com sucesso!");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("users", usuarioRepository.findAll());
        return "cadastro-usuario";
    }

    // Apenas ADMIN pode editar
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editar")
    public String editarUsuario(@RequestParam("id") Long id, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
        } else {
            model.addAttribute("usuario", new Usuario());
            model.addAttribute("errorMessage", "Usuário não encontrado.");
        }
        model.addAttribute("users", usuarioRepository.findAll());
        return "cadastro-usuario";
    }

    // Botão listar redireciona pra própria tela
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("users", usuarioRepository.findAll());
        return "cadastro-usuario";
    }
}