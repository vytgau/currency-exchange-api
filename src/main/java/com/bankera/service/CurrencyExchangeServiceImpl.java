package com.bankera.service;

import com.bankera.domain.ExchangeRate;
import com.bankera.repository.ExchangeRateRepository;
import com.bankera.strategy.CurrencyExchangeStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of {@link CurrencyExchangeService}.
 */
@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final CurrencyExchangeStrategy currencyExchangeStrategy;

    @Override
    public BigDecimal exchange(final String fromCurrency, final String toCurrency, final BigDecimal amount) {
        final List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        return currencyExchangeStrategy.exchange(exchangeRates, fromCurrency, toCurrency, amount);
    }
}
