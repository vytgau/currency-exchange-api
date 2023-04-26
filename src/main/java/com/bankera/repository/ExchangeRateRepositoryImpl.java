package com.bankera.repository;

import com.bankera.domain.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ExchangeRateRepository}.
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

    private final ResultSetExtractor<List<ExchangeRate>> resultSetExtractor = JdbcTemplateMapperFactory
            .newInstance()
            .newResultSetExtractor(ExchangeRate.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ExchangeRateQueries queries;

    @Override
    public List<ExchangeRate> findAll() {
        return namedParameterJdbcTemplate.query(queries.getSelectAllQuery(), resultSetExtractor);
    }
}
