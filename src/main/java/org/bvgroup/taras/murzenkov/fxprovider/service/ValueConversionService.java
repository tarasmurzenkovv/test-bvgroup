package org.bvgroup.taras.murzenkov.fxprovider.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValueConversionService {
    public BigDecimal calculateConversionAmount(BigDecimal fxRate, BigDecimal amount) {
        return fxRate.multiply(amount);
    }
}
