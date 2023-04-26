package com.bankera.resource;

import com.bankera.dto.AmountDto;
import com.bankera.dto.CurrencyExchangeRequestDto;
import com.bankera.service.CurrencyExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Currency exchange resource.
 */
@RestController
@RequestMapping(path = "/currency-exchange", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CurrencyExchangeResource {

    private final CurrencyExchangeService currencyExchangeService;

    /**
     * Exchange amount from one currency to another.
     *
     * @param body the request body
     * @return response entity with exchanged amount
     */
    @PostMapping
    public ResponseEntity<AmountDto> exchange(@RequestBody @Valid final CurrencyExchangeRequestDto body) {
        final BigDecimal exchangedAmount = currencyExchangeService
                .exchange(body.getFromCurrency(), body.getToCurrency(), body.getAmount());

        return ResponseEntity
                .ok(AmountDto.builder()
                        .amount(exchangedAmount)
                        .build());
    }
}
