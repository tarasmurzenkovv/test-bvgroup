package org.bvgroup.taras.murzenkov.fxprovider.service;

import org.bvgroup.taras.murzenkov.fxprovider.model.exceptions.ValidationException;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValueConversionRequest;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validate(String currencyFrom, String currencyTo) {
        if (currencyFrom.equalsIgnoreCase(currencyTo)) {
            throw new ValidationException("Currency codes should be different");
        }
    }

    public void validate(ValueConversionRequest valueConversionRequest) {
        if (valueConversionRequest.currencyFrom()
                .equalsIgnoreCase(valueConversionRequest.currencyTo())) {
            throw new ValidationException("Currency codes should be different");
        }
    }
}
