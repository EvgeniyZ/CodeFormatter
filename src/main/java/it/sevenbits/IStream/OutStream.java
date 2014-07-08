package it.sevenbits.IStream;

import it.sevenbits.Exceptions.StreamException;

/**
 * Interface indicate output stream
 */
public interface OutStream {
    /**
     * Writes symbol to stream
     *
     * @param symbol symbol written to stream
     * @throws it.sevenbits.Exceptions.StreamException
     */
    void writeSymbol(char symbol) throws StreamException;

    /**
     * Close stream
     */
    void close() throws StreamException;
}
