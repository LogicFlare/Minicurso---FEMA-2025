package com.flare.minicurso_hibernate.service.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperacaoService {

    @Autowired
    private MetricsService metricsService;

    public void executarOperacao() {
        long inicio = System.currentTimeMillis();

        try {
            // Lógica de negócio
            log.info("Operação iniciada - usuario={}, acao={}",
                    "joao", "atualizar");

            metricsService.registrarOperacao("atualizar");

            log.info("Operação concluída com sucesso");

        } catch (Exception e) {
            log.error("Erro na operação - erro={}", e.getMessage(), e);
            metricsService.registrarErro("atualizar");
            throw e;

        } finally {
            long duracao = System.currentTimeMillis() - inicio;
            metricsService.registrarTempoOperacao("atualizar", duracao);
        }
    }
}

