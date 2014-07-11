package it.sevenbits.streams;

import it.sevenbits.exceptions.StreamException;

/**
 * Interface indicate output stream
 */
public interface OutStream {
    /**
     * Writes symbol to stream
     *
     * @param symbol symbol written to stream
     * @throws it.sevenbits.exceptions.StreamException
     */
    void writeSymbol(char symbol) throws StreamException;

    /**
     * Close stream
     *
     * @throws it.sevenbits.exceptions.StreamException
     */
    void close() throws StreamException;
}
