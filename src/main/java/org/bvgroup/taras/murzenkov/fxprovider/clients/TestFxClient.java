package org.bvgroup.taras.murzenkov.fxprovider.clients;

import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@ConditionalOnProperty(value = "fx.providers.test.enabled", havingValue = "true")
public class TestFxClient implements FxRateClient {

    @Override
    public FxRate getFxRate(String currencyFrom, String currencyTo) {
        return new FxRate(currencyFrom, currencyTo, BigDecimal.ONE);
    }

    @Override
    public List<FxRate> getFxRates(String currencyFrom) {
        return List.of(new FxRate(currencyFrom, "GBP", BigDecimal.ONE));
    }
}
