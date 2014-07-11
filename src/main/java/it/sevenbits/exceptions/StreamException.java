package it.sevenbits.exceptions;

/**
 * Exception arising in stream
 */
public class StreamException extends Exception {

    /**
     * Creates stream exception from error message
     */
    public StreamException(final String msg) {
        super(msg);
    }

    /**
     * Default constructor for exception
     */
    public StreamException() {
    }
}