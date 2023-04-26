package com.bankera.service;

import java.math.BigDecimal;

/**
 * Currency exchange service.
 */
public interface CurrencyExchangeService {

    /**
     * Exchanges the amount in the given <code>fromCurrency</code> to the specified <code>toCurrency</code>.
     *
     * @param fromCurrency the initial currency
     * @param toCurrency   the final currency
     * @param amount       the amount in the initial currency
     * @return the amount in the final currency
     */
    BigDecimal exchange(String fromCurrency, String toCurrency, BigDecimal amount);
}
