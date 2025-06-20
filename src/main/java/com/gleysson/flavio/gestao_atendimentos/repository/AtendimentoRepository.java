package com.gleysson.flavio.gestao_atendimentos.repository;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByNomeClienteContainingIgnoreCase(String nomeCliente);

    // Mude de String para Atendimento.CanalAtendimento
    List<Atendimento> findByCanalAtendimento(Atendimento.CanalAtendimento canalAtendimento); 

    List<Atendimento> findByDataHoraAtendimentoBetween(LocalDateTime inicio, LocalDateTime fim);
}