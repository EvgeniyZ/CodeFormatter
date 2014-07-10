package it.sevenbits.exceptions;

/**
 * Exception about errors in input stream data
 */
public class FormatterException extends Exception {

    /**
     * Creates formatter exception from error message
     * */
    public FormatterException(final String msg) {
        super(msg);
    }

    /**
     * Default constructor for exception
     * */
    public FormatterException() {
    }
}
