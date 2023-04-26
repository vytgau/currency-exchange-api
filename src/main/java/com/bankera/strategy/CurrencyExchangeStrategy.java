package com.bankera.strategy;

import com.bankera.domain.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Currency exchange strategy,
 */
public interface CurrencyExchangeStrategy {

    /**
     * Exchanges the amount in the given <code>fromCurrency</code> to the specified <code>toCurrency</code>
     * based on the given exchange rates.
     *
     * @param exchangeRates the exchange rates
     * @param fromCurrency  the initial currency
     * @param toCurrency    the final currency
     * @param amount        the amount in the initial currency
     * @return the amount in the final currency
     */
    BigDecimal exchange(List<ExchangeRate> exchangeRates, String fromCurrency, String toCurrency, BigDecimal amount);
}
