package org.bvgroup.taras.murzenkov.fxprovider.clients;

import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;

import java.util.List;

public interface FxRateClient {
    FxRate getFxRate(String currencyFrom, String currencyTo);

    List<FxRate> getFxRates(String currencyFrom);
}
