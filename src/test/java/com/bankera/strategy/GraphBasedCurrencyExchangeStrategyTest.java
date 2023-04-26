package com.bankera.strategy;

import com.bankera.config.CurrencyExchangeProperties;
import com.bankera.domain.ExchangeRate;
import com.bankera.error.CurrencyConvertException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GraphBasedCurrencyExchangeStrategyTest {

    private static final Integer SCALE = 18;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final String GBP = "GBP";
    private static final String JPY = "JPY";
    private static final String CHF = "CHF";

    private GraphBasedCurrencyExchangeStrategy strategy;

    private List<ExchangeRate> exchangeRates;

    @BeforeEach
    void setUp() {
        final CurrencyExchangeProperties properties = new CurrencyExchangeProperties();
        properties.setScale(SCALE);
        properties.setRoundingMode(ROUNDING_MODE);

        strategy = new GraphBasedCurrencyExchangeStrategy(properties);

        exchangeRates = new ArrayList<>();
        exchangeRates.add(ExchangeRate.builder().fromCurrency(USD).toCurrency(EUR).exchangeRate(BigDecimal.valueOf(0.8)).build());
        exchangeRates.add(ExchangeRate.builder().fromCurrency(EUR).toCurrency(GBP).exchangeRate(BigDecimal.valueOf(0.9)).build());
        exchangeRates.add(ExchangeRate.builder().fromCurrency(JPY).toCurrency(CHF).exchangeRate(BigDecimal.valueOf(0.0067)).build());
    }

    @Test
    void shouldConvertCurrenciesWithoutDirectExchangeRateWhenPossible() {
        final BigDecimal exchangedAmount = strategy.exchange(exchangeRates, USD, GBP, BigDecimal.valueOf(5));

        assertEquals(0, exchangedAmount.compareTo(BigDecimal.valueOf(3.6)));
    }

    @Test
    void shouldConvertCurrenciesWithDirectExchangeRate() {
        final BigDecimal exchangedAmount = strategy.exchange(exchangeRates, USD, EUR, BigDecimal.valueOf(5));

        assertEquals(0, exchangedAmount.compareTo(BigDecimal.valueOf(4)));
    }

    @Test
    void shouldReturnSameAmountWhenSourceCurrencyMatchesTargetCurrency() {
        final BigDecimal amount = BigDecimal.valueOf(5);
        final BigDecimal exchangedAmount = strategy.exchange(exchangeRates, USD, USD, amount);

        assertEquals(0, exchangedAmount.compareTo(amount));
    }

    @Test
    void shouldConvertCurrenciesByReverseExchangeRates() {
        final BigDecimal exchangedAmount = strategy.exchange(exchangeRates, GBP, USD, BigDecimal.valueOf(5));

        assertEquals(0, exchangedAmount.compareTo(new BigDecimal("6.944444444444444443")));
    }

    @Test
    void shouldThrowExceptionWhenUnableToConvertCurrenciesBecauseExchangeRatesPathBetweenThemIsNotFound() {
        assertThrows(CurrencyConvertException.class, () -> strategy.exchange(exchangeRates, USD, JPY, BigDecimal.ONE));
    }

    @Test
    void shouldThrowExceptionWhenGivenAnEmptyExchangeRatesList() {
        assertThrows(CurrencyConvertException.class, () -> strategy.exchange(Collections.emptyList(), USD, EUR, BigDecimal.ONE));
    }

    @Test
    void shouldThrowExceptionWhenGivenUnsupportedSourceCurrency() {
        assertThrows(CurrencyConvertException.class, () -> strategy.exchange(exchangeRates, "UNSUPPORTED", EUR, BigDecimal.valueOf(5)));
    }

    @Test
    void shouldThrowExceptionWhenGivenUnsupportedTargetCurrency() {
        assertThrows(CurrencyConvertException.class, () -> strategy.exchange(exchangeRates, USD, "UNSUPPORTED", BigDecimal.valueOf(5)));
    }
}