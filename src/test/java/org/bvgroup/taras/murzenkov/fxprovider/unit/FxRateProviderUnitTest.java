package org.bvgroup.taras.murzenkov.fxprovider.unit;

import org.bvgroup.taras.murzenkov.fxprovider.clients.FxRateClient;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValueConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValuesConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.ValueConversion;
import org.bvgroup.taras.murzenkov.fxprovider.service.FxRateProvider;
import org.bvgroup.taras.murzenkov.fxprovider.service.ValidationService;
import org.bvgroup.taras.murzenkov.fxprovider.service.ValueConversionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FxRateProviderUnitTest {
    private final ValidationService validationService = Mockito.mock(ValidationService.class);
    private final FxRateClient fxRateClient = Mockito.mock(FxRateClient.class);
    private final ValueConversionService valueConversionService = Mockito.mock(ValueConversionService.class);
    private final FxRateProvider fxRateProvider = new FxRateProvider(
            validationService,
            fxRateClient,
            valueConversionService
    );

    @Test
    public void shouldProperlyProcessRequestToGetFxRate() {
        final var currencyFrom = "USD";
        final var currencyTo = "GBP";
        Mockito.doNothing().when(validationService).validate(currencyFrom, currencyTo);
        Mockito.when(fxRateClient.getFxRate(currencyFrom, currencyTo))
                .thenReturn(new FxRate(currencyFrom, currencyTo, BigDecimal.ONE));
        Mockito.when(valueConversionService.calculateConversionAmount(BigDecimal.ONE, BigDecimal.ONE))
                .thenReturn(BigDecimal.ONE);
        final var valueConversionRequest = new ValueConversionRequest(currencyFrom, currencyTo, BigDecimal.ONE);
        final var result = fxRateProvider.convert(valueConversionRequest);
        assertThat(result).isEqualTo(new ValueConversion(currencyFrom, currencyTo, BigDecimal.ONE));
        Mockito.verify(validationService).validate(valueConversionRequest);
        Mockito.verify(fxRateClient).getFxRate(currencyFrom, currencyTo);
        Mockito.verify(valueConversionService).calculateConversionAmount(BigDecimal.ONE, BigDecimal.ONE);
    }

    @Test
    public void shouldThrowExceptionAndNotMakeACallToExternalProviderWhenTheSameCurrencyIsSupplied() {
        final var currencyFrom = "USD";
        final var currencyTo = "USD";
        final var valueConversionRequest = new ValueConversionRequest(currencyFrom, currencyTo, BigDecimal.ONE);
        Mockito.doCallRealMethod().when(validationService).validate(valueConversionRequest);
        Mockito.when(fxRateClient.getFxRate(currencyFrom, currencyTo))
                .thenReturn(new FxRate(currencyFrom, currencyTo, BigDecimal.ONE));
        Mockito.when(valueConversionService.calculateConversionAmount(BigDecimal.ONE, BigDecimal.ONE))
                .thenReturn(BigDecimal.ONE);

        assertThatThrownBy(() -> fxRateProvider.convert(valueConversionRequest))
                .hasMessage("Currency codes should be different");
        Mockito.verify(validationService).validate(valueConversionRequest);
        Mockito.verify(fxRateClient, Mockito.never()).getFxRate(Mockito.any(), Mockito.any());
        Mockito.verify(valueConversionService, Mockito.never()).calculateConversionAmount(BigDecimal.ONE, BigDecimal.ONE);
    }
}
