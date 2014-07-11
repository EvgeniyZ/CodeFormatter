package it.sevenbits.filestream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.streams.OutStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class implements output stream as FileOutputStream and write to current stream
 */
public class FileOutStream implements OutStream {

    /**
     * Output stream
     */
    private FileOutputStream writer;

    /**
     * Constructor FileInStream
     *
     * @throws it.sevenbits.exceptions.StreamException
     */
    public FileOutStream(final String fileName) throws StreamException {
        try {
            writer = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("Cannot create file write stream. ");
        }
    }

    /**
     * @see it.sevenbits.streams.OutStream#writeSymbol(char)
     */
    @Override
    public final void writeSymbol(final char symbol) throws StreamException {
        try {
            writer.write(symbol);
        } catch (IOException e) {
            throw new StreamException("Cannot write symbol in file. ");
        }
    }

    /**
     * @see it.sevenbits.streams.OutStream#close()
     */
    @Override
    public final void close() throws StreamException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new StreamException("Cannot close output file. ");
        }
    }
}
