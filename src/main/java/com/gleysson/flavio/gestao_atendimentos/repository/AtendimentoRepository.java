package com.gleysson.flavio.gestao_atendimentos.repository;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.model.Usuario;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>, JpaSpecificationExecutor<Atendimento> {

    // --- Métodos de Busca Existentes ---
    List<Atendimento> findByNomeClienteContainingIgnoreCase(String nomeCliente);
    List<Atendimento> findByCanalAtendimento(Atendimento.CanalAtendimento canalAtendimento);
    List<Atendimento> findByDataHoraAtendimentoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Atendimento> findByUsuarioAtendente(Usuario usuarioAtendente);

    // --- Métodos para o BI (Corrigidos e com novos métodos semanais) ---
    @Query("SELECT COUNT(a) FROM Atendimento a")
    Long countTotalAtendimentos();

    @Query("SELECT COUNT(a) FROM Atendimento a WHERE EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP)")
    Long countMonthlyAtendimentos();

    @Query("SELECT COUNT(a) FROM Atendimento a WHERE EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP)")
    Long countDailyAtendimentos();

    // Novo método semanal com parâmetro
    @Query("SELECT COUNT(a) FROM Atendimento a WHERE a.dataHoraAtendimento >= :dataInicial")
    Long countWeeklyAtendimentos(@Param("dataInicial") LocalDateTime dataInicial);

    // --- TOP 10 Clientes
    @Query("SELECT a.nomeCliente, COUNT(a.id) FROM Atendimento a GROUP BY a.nomeCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10ClientesAtendidosTotal();

    @Query("SELECT a.nomeCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.nomeCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10ClientesAtendidosDiario();

    @Query("SELECT a.nomeCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.nomeCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10ClientesAtendidosMensal();

    @Query("SELECT a.nomeCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.nomeCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10ClientesAtendidosAnual();
    
    // Novo método semanal com parâmetro
    @Query("SELECT a.nomeCliente, COUNT(a.id) FROM Atendimento a WHERE a.dataHoraAtendimento >= :dataInicial GROUP BY a.nomeCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10ClientesAtendidosSemanal(@Param("dataInicial") LocalDateTime dataInicial);

    // --- TOP 10 Empresas
    @Query("SELECT a.empresaCliente, COUNT(a.id) FROM Atendimento a GROUP BY a.empresaCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10EmpresasAtendidasTotal();

    @Query("SELECT a.empresaCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.empresaCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10EmpresasAtendidasDiario();

    @Query("SELECT a.empresaCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.empresaCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10EmpresasAtendidasMensal();

    @Query("SELECT a.empresaCliente, COUNT(a.id) FROM Atendimento a WHERE EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY a.empresaCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10EmpresasAtendidasAnual();

    // Novo método semanal com parâmetro
    @Query("SELECT a.empresaCliente, COUNT(a.id) FROM Atendimento a WHERE a.dataHoraAtendimento >= :dataInicial GROUP BY a.empresaCliente ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10EmpresasAtendidasSemanal(@Param("dataInicial") LocalDateTime dataInicial);

    // --- TOP 10 Atendentes
    @Query("SELECT u.fullName, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u GROUP BY u.fullName ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10AtendentesTotal();

    @Query("SELECT u.fullName, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.fullName ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10AtendentesDiario();

    @Query("SELECT u.fullName, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.fullName ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10AtendentesMensal();

    @Query("SELECT u.fullName, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.fullName ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10AtendentesAnual();
    
    // Novo método semanal com parâmetro
    @Query("SELECT u.fullName, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE a.dataHoraAtendimento >= :dataInicial GROUP BY u.fullName ORDER BY COUNT(a.id) DESC LIMIT 10")
    List<Object[]> findTop10AtendentesSemanal(@Param("dataInicial") LocalDateTime dataInicial);

    // --- Atendimentos por Departamento
    @Query("SELECT u.departamento, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u GROUP BY u.departamento")
    List<Object[]> countAtendimentosPerDepartamentoTotal();

    @Query("SELECT u.departamento, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.departamento")
    List<Object[]> countAtendimentosPerDepartamentoDiario();

    @Query("SELECT u.departamento, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.departamento")
    List<Object[]> countAtendimentosPerDepartamentoMensal();

    @Query("SELECT u.departamento, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) GROUP BY u.departamento")
    List<Object[]> countAtendimentosPerDepartamentoAnual();
    
    // Novo método semanal com parâmetro
    @Query("SELECT u.departamento, COUNT(a.id) FROM Atendimento a JOIN a.usuarioAtendente u WHERE a.dataHoraAtendimento >= :dataInicial GROUP BY u.departamento")
    List<Object[]> countAtendimentosPerDepartamentoSemanal(@Param("dataInicial") LocalDateTime dataInicial);
    
    // --- Métodos para atendimentos do usuário logado ---
    Long countByUsuarioAtendente(Usuario usuarioAtendente);

    @Query("SELECT COUNT(a) FROM Atendimento a WHERE a.usuarioAtendente = :usuarioAtendente AND EXTRACT(DAY FROM a.dataHoraAtendimento) = EXTRACT(DAY FROM CURRENT_TIMESTAMP) AND EXTRACT(MONTH FROM a.dataHoraAtendimento) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM a.dataHoraAtendimento) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP)")
    Long countDailyByUsuarioAtendente(@Param("usuarioAtendente") Usuario usuarioAtendente);
    
    // Novo método semanal para o usuário logado
    @Query("SELECT COUNT(a) FROM Atendimento a WHERE a.usuarioAtendente = :usuarioAtendente AND a.dataHoraAtendimento >= :dataInicial")
    Long countWeeklyByUsuarioAtendente(@Param("usuarioAtendente") Usuario usuarioAtendente, @Param("dataInicial") LocalDateTime dataInicial);
=======
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
>>>>>>> cb233c539ce045cc47cef0f5933a2b64b8ec5509
}