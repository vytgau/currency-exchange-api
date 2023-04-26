package com.bankera.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Currency exchange request DTO.
 */
@Getter
@Setter
public class CurrencyExchangeRequestDto {

    @NotBlank
    private String fromCurrency;

    @NotBlank
    private String toCurrency;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;
}
