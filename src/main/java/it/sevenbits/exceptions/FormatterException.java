package it.sevenbits.exceptions;

/**
 * Exception about errors in input stream data
 */
public class FormatterException extends Exception {

    /**
     * Creates formatter exception from error message
     */
    public FormatterException(final String msg) {
        super(msg);
    }

    /**
     * Creates formatter exception from another exception
     */
    public FormatterException(final Throwable cause) {
        super(cause);
    }

    /**
     * Default constructor for exception
     */
    public FormatterException() {
    }
}
