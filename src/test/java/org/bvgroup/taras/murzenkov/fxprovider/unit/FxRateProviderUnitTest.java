package org.bvgroup.taras.murzenkov.fxprovider.unit;

import org.bvgroup.taras.murzenkov.fxprovider.clients.FxRateClient;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.bvgroup.taras.murzenkov.fxprovider.service.FxRateProvider;
import org.bvgroup.taras.murzenkov.fxprovider.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FxRateProviderUnitTest {
    private final ValidationService validationService = Mockito.mock(ValidationService.class);
    private final FxRateClient fxRateClient = Mockito.mock(FxRateClient.class);
    private final FxRateProvider fxRateProvider = new FxRateProvider(
            validationService,
            fxRateClient
    );

    @Test
    public void shouldProperlyProcessRequestToGetFxRate() {
        final var currencyFrom = "USD";
        final var currencyTo = "GBP";
        Mockito.doNothing().when(validationService).validate(currencyFrom, currencyTo);
        Mockito.when(fxRateClient.getFxRate(currencyFrom, currencyTo))
                .thenReturn(new FxRate(currencyFrom, currencyTo, BigDecimal.ONE));
        final var result = fxRateProvider.getFxRate(currencyFrom, currencyTo);
        assertThat(result).isEqualTo(new FxRate(currencyFrom, currencyTo, BigDecimal.ONE));
        Mockito.verify(validationService).validate(currencyFrom, currencyTo);
        Mockito.verify(fxRateClient).getFxRate(currencyFrom, currencyTo);
    }

    @Test
    public void shouldThrowExceptionAndNotMakeACallToExternalProviderWhenTheSameCurrencyIsSupplied() {
        final var currencyFrom = "USD";
        final var currencyTo = "USD";
        Mockito.doCallRealMethod().when(validationService).validate(currencyFrom, currencyTo);
        Mockito.when(fxRateClient.getFxRate(currencyFrom, currencyTo))
                .thenReturn(new FxRate(currencyFrom, currencyTo, BigDecimal.ONE));

        assertThatThrownBy(() -> fxRateProvider.getFxRate(currencyFrom, currencyTo))
                .hasMessage("Currency codes should be different");
        Mockito.verify(validationService).validate(currencyFrom, currencyTo);
        Mockito.verify(fxRateClient, Mockito.never()).getFxRate(Mockito.any(), Mockito.any());
    }
}
