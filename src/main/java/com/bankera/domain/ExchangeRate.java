package com.bankera.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Exchange rate entity.
 */
@Getter
@Setter
@Builder
public class ExchangeRate {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal exchangeRate;
}
