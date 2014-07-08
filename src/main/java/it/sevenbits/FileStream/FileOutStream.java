package it.sevenbits.FileStream;

import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.IStream.OutStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class implements output stream as FileOutputStream and write to current stream
 */
public class FileOutStream implements OutStream {
    private FileOutputStream writer;

    public FileOutStream() {
    }

    /**
     * Constructor FileInStream
     *
     * @throws it.sevenbits.Exceptions.StreamException
     */
    public FileOutStream(final String fileName) throws StreamException {
        try {
            writer = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("Cannot create file write stream. ");
        }
    }

    /**
     * Class implements output stream as writer and write to current stream
     *
     * @see it.sevenbits.IStream.OutStream#writeSymbol(char)
     */
    @Override
    public void writeSymbol(final char symbol) throws StreamException {
        try {
            writer.write(symbol);
        } catch (IOException e) {
            throw new StreamException("Cannot write symbol in file. ");
        }
    }

    /**
     * @see it.sevenbits.IStream.OutStream#close()
     */
    @Override
    public void close() throws StreamException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new StreamException("Cannot close output file. ");
        }
    }
}
