package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Mantenha este @Autowired aqui

    // NOVO MÉTODO PARA BUSCAR ATENDIMENTOS POR USUÁRIO
    public List<Atendimento> buscarAtendimentosPorUsuario(Usuario usuario) {
        // Garante que o usuário não é nulo antes de buscar
        if (usuario == null) {
            return Collections.emptyList();
        }
        List<Atendimento> atendimentos = atendimentoRepository.findByUsuarioAtendente(usuario);
        return atendimentos != null ? atendimentos : Collections.emptyList();
    }

    public List<Atendimento> buscarTodosAtendimentos() {
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        return atendimentos != null ? atendimentos : Collections.emptyList();
    }

    public Optional<Atendimento> buscarAtendimentoPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento salvarAtendimento(Atendimento atendimento) {
        if (atendimento.getDataHoraAtendimento() == null) {
            atendimento.setDataHoraAtendimento(LocalDateTime.now());
        }

        // NOVO: Associar o usuário logado ao atendimento se for um NOVO atendimento
        if (atendimento.getId() == null || atendimento.getUsuarioAtendente() == null) { // Adicionado verificação para permitir re-salvar com atendente
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                String username = authentication.getName();

                // CORREÇÃO AQUI: Chamar findByUsername do repository que retorna Optional
                Optional<Usuario> usuarioLogadoOpt = usuarioRepository.findByUsername(username);

                if (usuarioLogadoOpt.isPresent()) {
                    atendimento.setUsuarioAtendente(usuarioLogadoOpt.get());
                } else {
                    System.err.println("Aviso: Usuário logado '" + username + "' não encontrado no banco de dados ao salvar atendimento ID: " + atendimento.getId());
                }
            } else {
                System.err.println("Aviso: Tentativa de salvar atendimento sem usuário autenticado.");
            }
        }

        return atendimentoRepository.save(atendimento);
    }

    public void deletarAtendimento(Long id) {
        atendimentoRepository.deleteById(id);
    }

    public List<Atendimento> buscarPorNomeCliente(String nomeCliente) {
        return atendimentoRepository.findByNomeClienteContainingIgnoreCase(nomeCliente);
    }

    public List<Atendimento> buscarPorCanal(Atendimento.CanalAtendimento canal) {
        return atendimentoRepository.findByCanalAtendimento(canal);
    }

    public List<Atendimento> buscarPorCanalPeloNome(String canalAtendimentoString) {
        try {
            Atendimento.CanalAtendimento canal = Atendimento.CanalAtendimento.valueOf(canalAtendimentoString.toUpperCase());
            return atendimentoRepository.findByCanalAtendimento(canal);
        } catch (IllegalArgumentException e) {
            System.err.println("Canal de atendimento inválido: " + canalAtendimentoString + " - Erro: " + e.getMessage());
            return List.of();
        }
    }

    public List<Atendimento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return atendimentoRepository.findByDataHoraAtendimentoBetween(inicio, fim);
    }
}