package it.sevenbits.exceptions;

/**
 * Exception about discrepancy open and close brackets in stream data
 */
public class NotEnoughBracketsException extends Exception {

    /**
     *
     * */
    public NotEnoughBracketsException(final String msg) {
        super(msg);
    }

    /**
     *
     * */
    public NotEnoughBracketsException() {
    }
}
