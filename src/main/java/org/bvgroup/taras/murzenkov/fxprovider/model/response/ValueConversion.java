package org.bvgroup.taras.murzenkov.fxprovider.model.response;

import java.math.BigDecimal;

public record ValueConversion(
        String currencyFrom,
        String currencyTo,
        BigDecimal amount
) {
}
