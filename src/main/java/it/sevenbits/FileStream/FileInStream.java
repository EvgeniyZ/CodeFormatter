package it.sevenbits.FileStream;

import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.IStream.InStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class implements input stream as FileInputStream and read from this stream
 */
public class FileInStream implements InStream {
    private FileInputStream reader;

    public FileInStream() {
    }

    /**
     * Constructor FileInStream
     *
     * @throws it.sevenbits.Exceptions.StreamException
     */
    public FileInStream(final String fileName) throws StreamException {
        try {
            reader = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("Cannot create file read stream. ");
        }
    }

    /**
     * @see it.sevenbits.IStream.InStream#readSymbol()
     */
    @Override
    public char readSymbol() throws StreamException {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            throw new StreamException("Problem to read from stream. ");
        }
    }

    /**
     * @see it.sevenbits.IStream.InStream#isEnd()
     */
    @Override
    public boolean isEnd() throws StreamException {
        try {
            return reader.available() == 0;
        } catch (IOException e) {
            throw new StreamException("Can't find end of file. ");
        }
    }

    /**
     * @see it.sevenbits.IStream.InStream#close()
     */
    @Override
    public void close() throws StreamException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new StreamException("Problem with closing stream. ");
        }
    }
}
