package org.bvgroup.taras.murzenkov.fxprovider.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CacheConfiguration {
    private @Value("${fx.rates.cache.ttl}") Duration fxRateCacheTtl;

    @Bean
    public CacheManager fxRateCache() {
        return getCaffeineCacheManager(fxRateCacheTtl);
    }

    private CaffeineCacheManager getCaffeineCacheManager(Duration cacheTtl) {
        final var caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(cacheTtl));
        return caffeineCacheManager;
    }
}
