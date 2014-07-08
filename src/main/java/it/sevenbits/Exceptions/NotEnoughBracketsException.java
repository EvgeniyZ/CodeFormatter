package it.sevenbits.Exceptions;

/**
 * Exception about discrepancy open and close brackets in stream data
 */
public class NotEnoughBracketsException extends Exception {
    NotEnoughBracketsException(final String msg) {
        super(msg);
    }

    NotEnoughBracketsException() {
    }
}
