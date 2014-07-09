package it.sevenbits.IStream;

import it.sevenbits.exceptions.StreamException;

/**
 * Interface indicate input stream
 */
public interface InStream {
    /**
     * Reads a symbol from stream
     *
     * @return the next symbol code or -1 if stream out of data
     * @throws it.sevenbits.exceptions.StreamException
     */
    char readSymbol() throws StreamException;

    /**
     * Close stream
     *
     * @throws it.sevenbits.exceptions.StreamException
     */
    void close() throws StreamException;

    /**
     * Checking end of stream data, true if we reach end else false
     *
     * @return boolean
     * @throws it.sevenbits.exceptions.StreamException
     */
    boolean isEnd() throws StreamException;
}
