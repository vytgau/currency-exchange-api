package com.bankera.strategy;

import com.bankera.config.CurrencyExchangeProperties;
import com.bankera.domain.ExchangeRate;
import com.bankera.error.CurrencyConvertException;
import lombok.RequiredArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * NOTE: made an assumption here that currency conversion via multiple chainable exchange rates is allowed,
 * but I'm not sure if that is a correct behavior.
 * <p>
 * Graph based currency exchange strategy which attempts to exchange currencies even if a direct exchange
 * rate is not provided.
 * <p>
 * For example, given the following exchange rates:
 * <ul>
 *     <li>fromCurrency=USD, toCurrency=EUR, exchangeRate=0.8</li>
 *     <li>fromCurrency=EUR, toCurrency=GBP, exchangeRate=0.9</li>
 * </ul>
 * this strategy would be able to exchange USD to GBP by first converting USD to EUR and then converting
 * the result to GBP.
 */
@Component
@RequiredArgsConstructor
public class GraphBasedCurrencyExchangeStrategy implements CurrencyExchangeStrategy {

    private final CurrencyExchangeProperties properties;

    @Override
    public BigDecimal exchange(final List<ExchangeRate> exchangeRates,
                               final String fromCurrency,
                               final String toCurrency,
                               final BigDecimal amount) {
        if (Objects.equals(fromCurrency, toCurrency)) {
            return amount;
        }

        final Graph<String, BigDecimal> exchangeRatesGraph = createExchangeRatesGraph(exchangeRates);
        if (!exchangeRatesGraph.containsVertex(fromCurrency)) {
            throw new CurrencyConvertException("Unsupported currency [" + fromCurrency + "].");
        } else if (!exchangeRatesGraph.containsVertex(toCurrency)) {
            throw new CurrencyConvertException("Unsupported currency [" + toCurrency + "].");
        }

        final GraphPath<String, BigDecimal> exchangeRatesPath = findExchangeRatesPath(exchangeRatesGraph, fromCurrency, toCurrency);
        if (exchangeRatesPath == null) {
            throw new CurrencyConvertException("Unable to convert currency [" + fromCurrency + "] to [" + toCurrency + "].");
        }

        BigDecimal exchangedAmount = amount;
        for (BigDecimal exchangeRate : exchangeRatesPath.getEdgeList()) {
            exchangedAmount = exchangedAmount.multiply(exchangeRate);
        }

        return exchangedAmount.setScale(properties.getScale(), properties.getRoundingMode());
    }

    /**
     * Creates an exchange rates graph in which vertices are currencies and edges are directed with weight
     * equal to exchange rate. Reverse exchange rates are also calculated and represented as edges in the graph.
     * <p>
     * For example, given the following exchange rates:
     * <ul>
     *     <li>fromCurrency=USD, toCurrency=EUR, exchangeRate=0.8</li>
     *     <li>fromCurrency=EUR, toCurrency=GBP, exchangeRate=0.9</li>
     * </ul>
     * this method would create a graph with 3 vertices:
     * <ul>
     *     <li>USD</li>
     *     <li>EUR</li>
     *     <li>GBP</li>
     * </ul>
     * and 4 edges:
     * <ul>
     *     <li>USD ---0.8---> EUR</li>
     *     <li>EUR --1/0.8--> USD</li>
     *     <li>EUR ---0.9---> GBP</li>
     *     <li>GBP --1/0.9--> EUR</li>
     * </ul>
     *
     * @param exchangeRates the exchange rates
     * @return the exchange rates graph
     */
    private Graph<String, BigDecimal> createExchangeRatesGraph(final List<ExchangeRate> exchangeRates) {
        final Graph<String, BigDecimal> graph = new DefaultDirectedGraph<>(BigDecimal.class);
        for (ExchangeRate exchangeRate : exchangeRates) {
            graph.addVertex(exchangeRate.getFromCurrency());
            graph.addVertex(exchangeRate.getToCurrency());
            graph.addEdge(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency(), exchangeRate.getExchangeRate());
        }

        // Add reverse exchange rates
        for (ExchangeRate exchangeRate : exchangeRates) {
            final BigDecimal reverseExchangeRate = BigDecimal.ONE
                    .divide(exchangeRate.getExchangeRate(), properties.getScale(), properties.getRoundingMode());
            graph.addEdge(exchangeRate.getToCurrency(), exchangeRate.getFromCurrency(), reverseExchangeRate);
        }
        return graph;
    }

    /**
     * Finds a path between two vertices (currencies) in the exchange rates graph.
     *
     * @param graph        the exchange rates graph
     * @param fromCurrency the source currency
     * @param toCurrency   the destination currency
     * @return exchange rates path or null if not found
     */
    private GraphPath<String, BigDecimal> findExchangeRatesPath(final Graph<String, BigDecimal> graph,
                                                                final String fromCurrency,
                                                                final String toCurrency) {
        return DijkstraShortestPath.findPathBetween(graph, fromCurrency, toCurrency);
    }
}
