package com.gleysson.flavio.gestao_atendimentos.repository;

import com.gleysson.flavio.gestao_atendimentos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos personalizados podem ser definidos aqui se necessário
}
