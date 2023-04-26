package com.bankera.error;

/**
 * Currency convert exception.
 */
public class CurrencyConvertException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public CurrencyConvertException(final String message) {
        super(message);
    }
}
