package org.bvgroup.taras.murzenkov.fxprovider.model.request;

import java.math.BigDecimal;

public record CurrencyAmountToConvertTo(String currencyTo,
                                        BigDecimal amount) {
}
