package org.bvgroup.taras.murzenkov.fxprovider.service;

import lombok.RequiredArgsConstructor;
import org.bvgroup.taras.murzenkov.fxprovider.clients.FxRateClient;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValueConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValuesConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.ValueConversion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FxRateProvider {
    private final ValidationService validationService;
    private final FxRateClient fxRateClient;

    public FxRate getFxRate(String currencyFrom, String currencyTo) {
        validationService.validate(currencyFrom, currencyTo);
        return fxRateClient.getFxRate(currencyFrom, currencyTo);
    }

    public List<FxRate> getFxRates(String currency) {
        return fxRateClient.getFxRates(currency);
    }

}
