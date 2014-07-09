package it.sevenbits.filestream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.IStream.InStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class implements input stream as FileInputStream and read from this stream
 */
public class FileInStream implements InStream {

    /**
     * Input stream
     * */
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
            throw new StreamException("Cannot create file read stream. ");
        }
    }

    /**
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.IStream.InStream#readSymbol()
     */
    @Override
    public final char readSymbol() throws StreamException {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            throw new StreamException("Problem to read from stream. ");
        }
    }

    /**
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.IStream.InStream#isEnd()
     */
    @Override
    public final boolean isEnd() throws StreamException {
        try {
            return reader.available() == 0;
        } catch (IOException e) {
            throw new StreamException("Can't find end of file. ");
        }
    }

    /**
     * @throws it.sevenbits.exceptions.StreamException
     * @see it.sevenbits.IStream.InStream#close()
     */
    @Override
    public final void close() throws StreamException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new StreamException("Problem with closing stream. ");
        }
    }
}
