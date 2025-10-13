package com.flare.minicurso_hibernate.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // configuração de boas práticas para não usar muita memória RAM
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                // limpa depois de 10 minutos independende se foi acessado ou não
                .expireAfterWrite(Duration.ofMinutes(10))
                // limpa se não for acessado por 5 minutos
                .expireAfterAccess(Duration.ofMinutes(5))
                .recordStats()
        );

        List<String> cacheNames = List.of("alunos-all-search-pageable", "alunos-all-search");

        cacheManager.setCacheNames(cacheNames);

        return cacheManager;

    }
}
