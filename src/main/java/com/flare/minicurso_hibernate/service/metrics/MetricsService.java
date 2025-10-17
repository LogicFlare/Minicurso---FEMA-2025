package com.flare.minicurso_hibernate.service.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MetricsService {

    private final MeterRegistry meterRegistry;
    private final Counter operacoesCounter;
    private final Counter errosCounter;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.operacoesCounter = Counter.builder("operacoes_total")
                .description("Total de operações realizadas")
                .tag("tipo", "geral")
                .register(meterRegistry);

        this.errosCounter = Counter.builder("erros_total")
                .description("Total de erros")
                .register(meterRegistry);
    }

    public void registrarOperacao(String tipo) {
        Counter.builder("operacoes_total")
                .tag("tipo", tipo)
                .register(meterRegistry)
                .increment();
    }

    public void registrarErro(String tipo) {
        Counter.builder("erros_total")
                .tag("tipo", tipo)
                .register(meterRegistry)
                .increment();
    }

    public void registrarTempoOperacao(String operacao, long tempoMs) {
        Timer.builder("operacao_duracao")
                .tag("operacao", operacao)
                .register(meterRegistry)
                .record(tempoMs, TimeUnit.MILLISECONDS);
    }

    public void registrarOperacaoComLocalizacao(String tipo, String pais, String latitude, String longitude) {
        Counter.builder("operacoes_geolocalizadas")
                .tag("tipo", tipo)
                .tag("pais", pais)
                .tag("latitude", latitude)
                .tag("longitude", longitude)
                .description("Operações com dados de geolocalização")
                .register(meterRegistry)
                .increment();
    }

}
