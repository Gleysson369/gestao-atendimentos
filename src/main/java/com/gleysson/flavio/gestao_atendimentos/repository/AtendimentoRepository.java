package com.gleysson.flavio.gestao_atendimentos.repository;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // NOVO IMPORT

// Adicione JpaSpecificationExecutor<Atendimento>
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>, JpaSpecificationExecutor<Atendimento> {

    List<Atendimento> findByNomeClienteContainingIgnoreCase(String nomeCliente);

    List<Atendimento> findByCanalAtendimento(Atendimento.CanalAtendimento canalAtendimento);

    List<Atendimento> findByDataHoraAtendimentoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Atendimento> findByUsuarioAtendente(Usuario usuarioAtendente);
}