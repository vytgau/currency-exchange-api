package com.bankera.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.math.RoundingMode;

/**
 * Currency exchange properties
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "currency.exchange")
public class CurrencyExchangeProperties {

    @Min(0)
    @NotNull
    private Integer scale;

    @NotNull
    private RoundingMode roundingMode;
}
