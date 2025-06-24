package com.gleysson.flavio.gestao_atendimentos.repository;

import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // NOVO IMPORT

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // CORREÇÃO: Método para buscar um usuário pelo username (ID de login)
    Optional<Usuario> findByUsername(String username); // AGORA RETORNA Optional
}