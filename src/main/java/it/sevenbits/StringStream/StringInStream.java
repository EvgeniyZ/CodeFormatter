package it.sevenbits.StringStream;

import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.IStream.InStream;

/**
 * Class implements input stream as StringBuffer and read from this stream
 * <p/>
 * position - current position in string
 */
public class StringInStream implements InStream {
    StringBuffer stringBuffer;
    int position;

    public StringInStream() {
    }

    public StringInStream(String inputString) {
        stringBuffer = new StringBuffer(inputString);
        position = 0;
    }

    public String getString() {
        return stringBuffer.toString();
    }

    /**
     * @see it.sevenbits.IStream.InStream#readSymbol()
     */
    @Override
    public char readSymbol() throws StreamException {
        position++;
        return stringBuffer.charAt(position - 1);
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
    public boolean isEnd() throws StreamException {
        try {
            return position == stringBuffer.length();
        } catch (NullPointerException e) {
            throw new StreamException("String doesn't not exists. ");
        }
    }
}
