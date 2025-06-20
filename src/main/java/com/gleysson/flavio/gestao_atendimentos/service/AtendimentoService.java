package com.gleysson.flavio.gestao_atendimentos.service;

import com.gleysson.flavio.gestao_atendimentos.model.Atendimento;
import com.gleysson.flavio.gestao_atendimentos.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public List<Atendimento> buscarTodosAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public Optional<Atendimento> buscarAtendimentoPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento salvarAtendimento(Atendimento atendimento) {
        if (atendimento.getDataHoraAtendimento() == null) {
            atendimento.setDataHoraAtendimento(LocalDateTime.now());
        }
        return atendimentoRepository.save(atendimento);
    }

    public void deletarAtendimento(Long id) {
        atendimentoRepository.deleteById(id);
    }

    public List<Atendimento> buscarPorNomeCliente(String nomeCliente) {
        return atendimentoRepository.findByNomeClienteContainingIgnoreCase(nomeCliente);
    }

    // Método para buscar por canal, agora passando o ENUM diretamente
    public List<Atendimento> buscarPorCanal(Atendimento.CanalAtendimento canal) { // <<< Mudei o tipo do parâmetro
        return atendimentoRepository.findByCanalAtendimento(canal); // <<< Agora chamando corretamente
    }

    // Se você ainda precisa de um método que recebe String (por exemplo, de um formulário web)
    // você pode ter um método auxiliar que faz a conversão:
    public List<Atendimento> buscarPorCanalPeloNome(String canalAtendimentoString) {
        try {
            Atendimento.CanalAtendimento canal = Atendimento.CanalAtendimento.valueOf(canalAtendimentoString.toUpperCase());
            return atendimentoRepository.findByCanalAtendimento(canal);
        } catch (IllegalArgumentException e) {
            // Log do erro, ou lance uma exceção de negócio, ou retorne uma lista vazia
            System.err.println("Canal de atendimento inválido: " + canalAtendimentoString);
            return List.of(); // Ou lance new ResponseStatusException(HttpStatus.BAD_REQUEST, "Canal inválido");
        }
    }


    public List<Atendimento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return atendimentoRepository.findByDataHoraAtendimentoBetween(inicio, fim);
    }
}