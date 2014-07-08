package it.sevenbits.Exceptions;

/**
 * Exception about errors in input stream data
 */
public class FormatterException extends Exception {

    public FormatterException(String msg) {
        super(msg);
    }

    public FormatterException() {
    }
}
