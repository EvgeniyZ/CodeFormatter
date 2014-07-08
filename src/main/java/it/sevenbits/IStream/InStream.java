package it.sevenbits.IStream;

import it.sevenbits.Exceptions.StreamException;

/**
 * Interface indicate input stream
 */
public interface InStream {
    /**
     * Reads a symbol from stream
     *
     * @return the next symbol code or -1 if stream out of data
     */
    char readSymbol() throws StreamException;

    /**
     * Close stream
     *
     * @throws it.sevenbits.Exceptions.StreamException
     */
    void close() throws StreamException;

    /**
     * Checking end of stream data, true if we reach end else false
     *
     * @return boolean
     */
    boolean isEnd() throws StreamException;
}
