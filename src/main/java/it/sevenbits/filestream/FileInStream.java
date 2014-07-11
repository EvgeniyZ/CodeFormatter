package it.sevenbits.filestream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.streams.InStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class implements input stream as FileInputStream and read from this stream
 */
public class FileInStream implements InStream {

    /**
     * Input stream
     */
    private FileInputStream reader;

    /**
     * Constructor FileInStream
     *
     * @param fileName - Name of file to open in stream
     * @throws it.sevenbits.exceptions.StreamException
     */
    public FileInStream(final String fileName) throws StreamException {
        try {
            reader = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("\nCannot create file read stream. \n");
        }
    }

    /**
     * @return char
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.streams.InStream#readSymbol()
     */
    @Override
    public final char readSymbol() throws StreamException {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            throw new StreamException("\nProblem to read from stream. \n");
        }
    }

    /**
     * @return boolean
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.streams.InStream#isEnd()
     */
    @Override
    public final boolean isEnd() throws StreamException {
        try {
            return reader.available() == 0;
        } catch (IOException e) {
            throw new StreamException("\nCan't find end of file. \n");
        }
    }

    /**
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.streams.InStream#close()
     */
    @Override
    public final void close() throws StreamException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new StreamException("\nProblem with closing stream. \n");
        }
    }
}
