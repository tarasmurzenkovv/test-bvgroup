package org.bvgroup.taras.murzenkov.fxprovider.service;

import lombok.RequiredArgsConstructor;
import org.bvgroup.taras.murzenkov.fxprovider.clients.FxRateClient;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValueConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValuesConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.ValueConversion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValueConversionService {
    private final ValidationService validationService;
    private final FxRateClient fxRateClient;

    public ValueConversion convert(ValueConversionRequest valueConversionRequest) {
        validationService.validate(valueConversionRequest);
        final var currencyFrom = valueConversionRequest.currencyFrom();
        final var currencyTo = valueConversionRequest.currencyTo();
        final var amount = valueConversionRequest.amount();
        final var fxRate = fxRateClient.getFxRate(currencyFrom, currencyTo);
        final var convertedValue = calculateConversionAmount(fxRate.rate(), amount);
        return new ValueConversion(currencyFrom, currencyTo, convertedValue);
    }

    public List<ValueConversion> convert(String currencyFrom, ValuesConversionRequest valuesConversionRequest) {
        return valuesConversionRequest
                .currencies()
                .stream()
                .map(request -> new ValueConversionRequest(
                        currencyFrom,
                        request.currencyTo(),
                        request.amount()
                ))
                .map(this::convert)
                .toList();
    }

    private BigDecimal calculateConversionAmount(BigDecimal fxRate, BigDecimal amount) {
        return fxRate.multiply(amount);
    }
}
