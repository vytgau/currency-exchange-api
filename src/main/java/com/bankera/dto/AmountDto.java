package com.bankera.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Amount DTO.
 */
@Getter
@Builder
public class AmountDto {

    private BigDecimal amount;
}
