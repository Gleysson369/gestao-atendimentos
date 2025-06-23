package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario; // Importar o modelo Usuario
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository; // Importar o repositório de Usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Importar para obter autenticação
import org.springframework.security.core.context.SecurityContextHolder; // Importar para obter o contexto de segurança
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // INJEÇÃO: Adicione o repositório de Usuario

    public List<Atendimento> buscarTodosAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public Optional<Atendimento> buscarAtendimentoPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento salvarAtendimento(Atendimento atendimento) {
        // Se a data e hora do atendimento não foram definidas, use a hora atual
        if (atendimento.getDataHoraAtendimento() == null) {
            atendimento.setDataHoraAtendimento(LocalDateTime.now());
        }

        // NOVO: Associar o usuário logado ao atendimento
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            // Obter o username do usuário logado (geralmente é o principal.getName())
            String username = authentication.getName();

            // Buscar o objeto Usuario completo no banco de dados
            Usuario usuarioLogado = usuarioRepository.findByUsername(username);

            if (usuarioLogado != null) {
                atendimento.setUsuarioAtendente(usuarioLogado); // Define o usuário que atendeu
            } else {
                // ISTO É CRÍTICO: Se o usuário autenticado não for encontrado no DB, há um problema.
                // Você pode logar, lançar uma exceção ou definir o atendente como nulo, dependendo da sua regra de negócio.
                // Por segurança, é bom que atendimentos sempre tenham um atendente, se o sistema exige login.
                System.err.println("Aviso: Usuário logado '" + username + "' não encontrado no banco de dados ao salvar atendimento ID: " + atendimento.getId());
                // Opcional: throw new RuntimeException("Usuário logado não encontrado no sistema.");
            }
        } else {
            // Se não há usuário autenticado (ex: se esta rota fosse acessível sem login, o que não é o caso aqui)
            System.err.println("Aviso: Tentativa de salvar atendimento sem usuário autenticado.");
            // Opcional: atendimento.setUsuarioAtendente(null);
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