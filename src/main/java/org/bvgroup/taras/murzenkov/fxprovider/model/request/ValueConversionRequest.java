package org.bvgroup.taras.murzenkov.fxprovider.model.request;

import java.math.BigDecimal;

public record ValueConversionRequest(
        String currencyFrom,
        String currencyTo,
        BigDecimal amount
) {
}
