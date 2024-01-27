package org.bvgroup.taras.murzenkov.fxprovider.model.response;

import java.math.BigDecimal;

public record FxRate(
        String currencyFrom,
        String currencyTo,
        BigDecimal rate
) {

}
