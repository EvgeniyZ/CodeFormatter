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
     * Creates formatter exception from stream exception
     */
    public FormatterException(final StreamException ex) {
        super(ex.getMessage());
    }

    /**
     * Creates formatter exception from stream exception
     */
    public FormatterException(final NotEnoughBracketsException ex) {
        super(ex.getMessage());
    }

    /**
     * Default constructor for exception
     */
    public FormatterException() {
    }
}
