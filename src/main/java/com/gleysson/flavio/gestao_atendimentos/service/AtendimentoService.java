package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.dto.RelatorioFiltros;
import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import com.gleysson.flavio.gestao_atendimentos.repository.UsuarioRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    // --- NOVO MÉTODO: CONSOLIDADO PARA BUSCA COM FILTROS E SEGURANÇA ---
    public List<Atendimento> buscarAtendimentosComFiltros(RelatorioFiltros filtros, boolean isAdmin, Usuario currentUser) {
        Specification<Atendimento> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtros do DTO
            if (filtros.getNomeCliente() != null && !filtros.getNomeCliente().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nomeCliente")), "%" + filtros.getNomeCliente().toLowerCase() + "%"));
            }
            if (filtros.getEmpresaCliente() != null && !filtros.getEmpresaCliente().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("empresaCliente")), "%" + filtros.getEmpresaCliente().toLowerCase() + "%"));
            }
            if (filtros.getDataInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataHoraAtendimento"), filtros.getDataInicio().atStartOfDay()));
            }
            if (filtros.getDataFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataHoraAtendimento"), filtros.getDataFim().atTime(LocalTime.MAX)));
            }
            if (filtros.getCanalAtendimento() != null && !filtros.getCanalAtendimento().isEmpty()) {
                try {
                    Atendimento.CanalAtendimento canal = Atendimento.CanalAtendimento.valueOf(filtros.getCanalAtendimento().toUpperCase());
                    predicates.add(cb.equal(root.get("canalAtendimento"), canal));
                } catch (IllegalArgumentException e) {
                    System.err.println("Canal de atendimento inválido no filtro: " + filtros.getCanalAtendimento());
                }
            }
            // NOVO FILTRO: Por ID do Atendente (se selecionado)
            if (filtros.getAtendenteId() != null) {
                predicates.add(cb.equal(root.get("usuarioAtendente").get("id"), filtros.getAtendenteId()));
            }

            // NOVO FILTRO: Por Departamento do Atendente (se selecionado)
            if (filtros.getDepartamentoAtendente() != null && !filtros.getDepartamentoAtendente().isEmpty()) {
                try {
                    predicates.add(cb.equal(root.get("usuarioAtendente").get("departamento"), Usuario.Departamento.valueOf(filtros.getDepartamentoAtendente())));
                } catch (IllegalArgumentException e) {
                    System.err.println("Departamento inválido no filtro: " + filtros.getDepartamentoAtendente());
                }
            }
            // NOVO FILTRO: Por Atendimento de Transferência
            if (filtros.getAtendimentoTransferencia() != null) {
                predicates.add(cb.equal(root.get("atendimentoTransferencia"), filtros.getAtendimentoTransferencia()));
            }
            // NOVO FILTRO: Por Protocolo de Atendimento
            if (filtros.getProtocoloAtendimento() != null && !filtros.getProtocoloAtendimento().isEmpty()) {
                predicates.add(cb.equal(root.get("protocoloAtendimento"), filtros.getProtocoloAtendimento()));
            }

            // --- LÓGICA DE SEGURANÇA INTEGRADA ---
            if (!isAdmin && currentUser != null) {
                predicates.add(cb.equal(root.get("usuarioAtendente"), currentUser));
            }

            // --- LÓGICA DE ORDENAÇÃO (opcional, mas boa prática) ---
            List<Order> orders = new ArrayList<>();
            if (filtros.getSortBy() != null && !filtros.getSortBy().isEmpty()) {
                Path<?> sortPath = root;
                if (filtros.getSortBy().contains(".")) {
                    String[] parts = filtros.getSortBy().split("\\.");
                    Join<Object, Object> join = root.join(parts[0]);
                    sortPath = join.get(parts[1]);
                } else {
                    sortPath = root.get(filtros.getSortBy());
                }
                
                if ("desc".equalsIgnoreCase(filtros.getSortDir())) {
                    orders.add(cb.desc(sortPath));
                } else {
                    orders.add(cb.asc(sortPath));
                }
            } else {
                orders.add(cb.desc(root.get("dataHoraAtendimento")));
            }
            query.orderBy(orders);

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return atendimentoRepository.findAll(spec);
    }

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
}