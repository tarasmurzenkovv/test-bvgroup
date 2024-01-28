package org.bvgroup.taras.murzenkov.fxprovider.clients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bvgroup.taras.murzenkov.fxprovider.configuration.properties.FreeCurrencyAppClientProperties;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "prod")
@RequiredArgsConstructor
public class FreeCurrencyAppClient implements FxRateClient {
    private final RestTemplate restTemplate;
    private final FreeCurrencyAppClientProperties freeCurrencyAppClientProperties;

    @Override
    @Cacheable("fxRateCache")
    public FxRate getFxRate(String currencyFrom, String currencyTo) {
        final var url = freeCurrencyAppClientProperties.getHost()
                + freeCurrencyAppClientProperties.getApiKey()
                + "&base_currency=" + currencyFrom
                + "&currencies=" + currencyTo;
        final var body = restTemplate.getForObject(url, FreeCurrencyAppClientFxRate.class);
        final var rate = body.data.get(currencyTo);
        return new FxRate(currencyFrom, currencyTo, rate);
    }

    @Override
    @Cacheable("fxRateCache")
    public List<FxRate> getFxRates(String currencyFrom) {
        final var url = freeCurrencyAppClientProperties.getHost()
                + freeCurrencyAppClientProperties.getApiKey()
                + "&base_currency=" + currencyFrom;
        return restTemplate
                .getForObject(url, FreeCurrencyAppClientFxRate.class)
                .data
                .entrySet()
                .stream().map(entry -> new FxRate(currencyFrom, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    private static class FreeCurrencyAppClientFxRate {
        private Map<String, BigDecimal> data;
    }
}
