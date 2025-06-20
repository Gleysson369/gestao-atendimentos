package com.gleysson.flavio.gestao_atendimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestaoAtendimentosApplication { // Não implementa mais CommandLineRunner

    public static void main(String[] args) {
        SpringApplication.run(GestaoAtendimentosApplication.class, args);
    }
}