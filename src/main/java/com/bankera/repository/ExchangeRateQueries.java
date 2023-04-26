package com.bankera.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Exchange rate queries.
 */
@Getter
@Component
public class ExchangeRateQueries {

    @Value("${exchangerate.select-all}")
    private String selectAllQuery;
}
