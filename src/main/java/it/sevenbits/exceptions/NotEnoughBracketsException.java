package it.sevenbits.exceptions;

/**
 * Exception about discrepancy open and close brackets in stream data
 */
public class NotEnoughBracketsException extends Exception {

    /**
     * Creates not enough bracket from error message
     * */
    public NotEnoughBracketsException(final String msg) {
        super(msg);
    }

    /**
     * Default constructor for exception
     * */
    public NotEnoughBracketsException() {
    }
}
