package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.dto.RelatorioFiltros;
import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order; // Importe Order
import jakarta.persistence.criteria.Path; // Importe Path
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Atendimento> buscarAtendimentosComFiltros(RelatorioFiltros filtros, boolean isAdmin, Usuario currentUser) {
        Specification<Atendimento> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Nome do Cliente
            if (filtros.getNomeCliente() != null && !filtros.getNomeCliente().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nomeCliente")), "%" + filtros.getNomeCliente().toLowerCase() + "%"));
            }
            // Filtro por Empresa do Cliente
            if (filtros.getEmpresaCliente() != null && !filtros.getEmpresaCliente().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("empresaCliente")), "%" + filtros.getEmpresaCliente().toLowerCase() + "%"));
            }

            // Filtro por Período de Atendimento
            if (filtros.getDataInicio() != null && filtros.getDataFim() != null) {
                LocalDateTime inicioDoDia = filtros.getDataInicio().atStartOfDay();
                LocalDateTime fimDoDia = filtros.getDataFim().atTime(LocalTime.MAX);
                predicates.add(cb.between(root.get("dataHoraAtendimento"), inicioDoDia, fimDoDia));
            } else if (filtros.getDataInicio() != null) {
                LocalDateTime inicioDoDia = filtros.getDataInicio().atStartOfDay();
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataHoraAtendimento"), inicioDoDia));
            } else if (filtros.getDataFim() != null) {
                LocalDateTime fimDoDia = filtros.getDataFim().atTime(LocalTime.MAX);
                predicates.add(cb.lessThanOrEqualTo(root.get("dataHoraAtendimento"), fimDoDia));
            }

            // Filtro por Canal de Atendimento
            if (filtros.getCanalAtendimento() != null && !filtros.getCanalAtendimento().isEmpty()) {
                try {
                    Atendimento.CanalAtendimento canal = Atendimento.CanalAtendimento.valueOf(filtros.getCanalAtendimento().toUpperCase());
                    predicates.add(cb.equal(root.get("canalAtendimento"), canal));
                } catch (IllegalArgumentException e) {
                    System.err.println("Canal de atendimento inválido no filtro: " + filtros.getCanalAtendimento());
                }
            }

            // NOVO FILTRO: Por ID do Atendente (se selecionado)
            Join<Atendimento, Usuario> usuarioJoin = null; // Declare fora do if para reutilizar

            if (filtros.getAtendenteId() != null) {
                usuarioJoin = root.join("usuarioAtendente"); // Cria o JOIN se ainda não foi criado
                predicates.add(cb.equal(usuarioJoin.get("id"), filtros.getAtendenteId()));
            }

            // NOVO FILTRO: Por Departamento do Atendente (se selecionado)
            if (filtros.getDepartamentoAtendente() != null && !filtros.getDepartamentoAtendente().isEmpty()) {
                if (usuarioJoin == null) { // Cria o JOIN apenas se não foi criado acima
                    usuarioJoin = root.join("usuarioAtendente");
                }
                // Garante que o departamento do atendente no banco é um Enum
                try {
                     predicates.add(cb.equal(usuarioJoin.get("departamento"), Usuario.Departamento.valueOf(filtros.getDepartamentoAtendente())));
                } catch (IllegalArgumentException e) {
                    System.err.println("Departamento inválido no filtro: " + filtros.getDepartamentoAtendente());
                }
            }

            // LÓGICA DE VISIBILIDADE (ADMIN vê tudo, USER vê os seus)
            if (!isAdmin && currentUser != null) {
                predicates.add(cb.equal(root.get("usuarioAtendente"), currentUser));
            }

            // --- LÓGICA DE ORDENAÇÃO AQUI DENTRO DA SPECIFICATION ---
            List<Order> orders = new ArrayList<>();
            Path<?> sortPath;

            if (filtros.getSortBy() != null && !filtros.getSortBy().isEmpty()) {
                if (filtros.getSortBy().contains(".")) {
                    // Ordenação por campo aninhado (ex: usuarioAtendente.fullName)
                    String[] parts = filtros.getSortBy().split("\\.");
                    if (parts.length == 2 && "usuarioAtendente".equals(parts[0])) {
                        if (usuarioJoin == null) { // Certifica-se de que o JOIN existe para ordenação
                            usuarioJoin = root.join("usuarioAtendente");
                        }
                        sortPath = usuarioJoin.get(parts[1]);
                    } else {
                        // Fallback para campo direto se o formato aninhado não for 'usuarioAtendente.campo'
                        sortPath = root.get(filtros.getSortBy());
                    }
                } else {
                    // Ordenação por campo direto do Atendimento
                    sortPath = root.get(filtros.getSortBy());
                }

                if ("desc".equalsIgnoreCase(filtros.getSortDir())) {
                    orders.add(cb.desc(sortPath));
                } else {
                    orders.add(cb.asc(sortPath));
                }
            } else {
                // Ordem padrão se não especificado
                orders.add(cb.desc(root.get("dataHoraAtendimento")));
            }
            query.orderBy(orders);
            // --- FIM DA LÓGICA DE ORDENAÇÃO NA SPECIFICATION ---

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Chama o findAll que aceita Specification (não precisa mais passar o Sort separado)
        return atendimentoRepository.findAll(spec);
    }

    // MÉTODOS EXISTENTES (mantidos ou ajustados se necessário)
    // ... (Seus outros métodos aqui, inalterados) ...
    public List<Atendimento> buscarTodosAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public List<Atendimento> buscarAtendimentosPorUsuario(Usuario usuario) {
        if (usuario == null) {
            return Collections.emptyList();
        }
        List<Atendimento> atendimentos = atendimentoRepository.findByUsuarioAtendente(usuario);
        return atendimentos != null ? atendimentos : Collections.emptyList();
    }

    public Optional<Atendimento> buscarAtendimentoPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento salvarAtendimento(Atendimento atendimento) {
        if (atendimento.getDataHoraAtendimento() == null) {
            atendimento.setDataHoraAtendimento(LocalDateTime.now());
        }

        if (atendimento.getId() == null || atendimento.getUsuarioAtendente() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                String username = authentication.getName();
                Optional<Usuario> usuarioLogadoOpt = usuarioRepository.findByUsername(username);
                if (usuarioLogadoOpt.isPresent()) {
                    atendimento.setUsuarioAtendente(usuarioLogadoOpt.get());
                } else {
                    System.err.println("Aviso: Usuário logado '" + username + "' não encontrado no banco de dados ao salvar atendimento.");
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