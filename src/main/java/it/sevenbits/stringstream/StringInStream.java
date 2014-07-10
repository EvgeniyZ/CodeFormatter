package it.sevenbits.stringstream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.IStream.InStream;

/**
 * Class implements input stream as StringBuffer and read from this stream
 *
 * currentPosition - current currentPosition in string
 */
public class StringInStream implements InStream {

    /**
     * Input stream based on string buffer
     * */
    private StringBuffer stringBuffer;

    /**
     * Current position in string buffer stream
     * */
    private int currentPosition = 0;

    /**
     * Default stream
     * */
    public StringInStream() {}

    /**
     * Creates stream from string
     * */
    public StringInStream(final String inputString) {
        stringBuffer = new StringBuffer(inputString);
        currentPosition = 0;
    }

    /**
     * Get string from input string buffer
     * @return string
     * */
    public final String getString() {
        return stringBuffer.toString();
    }

    /**
     * @see it.sevenbits.IStream.InStream#readSymbol()
     */
    @Override
    public final char readSymbol() throws StreamException {
        currentPosition++;
        return stringBuffer.charAt(currentPosition - 1);
    }

    /**
     * @see it.sevenbits.IStream.InStream#close()
     */
    @Override
    public void close() throws StreamException {
    }

    /**
     * @see it.sevenbits.IStream.InStream#isEnd()
     */
    @Override
    public final boolean isEnd() throws StreamException {
        try {
            return currentPosition == stringBuffer.length();
        } catch (NullPointerException e) {
            throw new StreamException("String doesn't not exists. ");
        }
    }
}
