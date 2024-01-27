package org.bvgroup.taras.murzenkov.fxprovider.model.request;

import java.util.List;

public record ValuesConversionRequest(

        List<CurrencyAmountToConvertTo> currencies
) {
}
