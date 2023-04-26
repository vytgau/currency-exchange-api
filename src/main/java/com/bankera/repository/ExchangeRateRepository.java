package com.bankera.repository;

import com.bankera.domain.ExchangeRate;

import java.util.List;

/**
 * Repository for {@link ExchangeRate} objects.
 */
public interface ExchangeRateRepository {

    /**
     * Finds all exchange rates.
     *
     * @return a list of exchange rates
     */
    List<ExchangeRate> findAll();
}
