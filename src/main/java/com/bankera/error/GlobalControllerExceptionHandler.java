package com.bankera.error;

import com.bankera.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles exceptions thrown in controllers.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * Handle currency convert exception.
     *
     * @param exception the currency convert exception
     * @return response entity
     */
    @ExceptionHandler(CurrencyConvertException.class)
    public ResponseEntity<ErrorDto> handleCurrencyConvertException(final CurrencyConvertException exception) {
        final String id = generateErrorId();

        LOG.error("[{}] Currency convert exception - {}", id, exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .id(id)
                        .message(exception.getMessage())
                        .build());
    }

    /**
     * Handle validation failure exception.
     *
     * @param exception the validation failure exception
     * @return response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final String id = generateErrorId();

        LOG.error("[{}] Validation failure - {}", id, exception.getMessage());

        final Map<String, Object> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .id(id)
                        .message("Bad request.")
                        .data(errors)
                        .build());
    }

    private String generateErrorId() {
        return UUID.randomUUID().toString();
    }
}
