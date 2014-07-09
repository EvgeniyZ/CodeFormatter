package it.sevenbits.stringstream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.IStream.OutStream;

/**
 * Class implements output stream as StringBuffer and write to current stream
 */
public class StringOutStream implements OutStream {

    /**
     *
     * */
    private StringBuffer stringBuffer;

    /**
     *
     * */
    public StringOutStream(final String outputString) {
        stringBuffer = new StringBuffer(outputString);
    }

    /**
     *
     * */
    public final String getString() {
        return stringBuffer.toString();
    }

    /**
     * @see it.sevenbits.IStream.OutStream#writeSymbol(char symbol)
     */
    @Override
    public final void writeSymbol(final char symbol) throws StreamException {
        stringBuffer.append(symbol);
    }

    /**
     * @see it.sevenbits.IStream.OutStream#close()
     */
    @Override
    public void close() throws StreamException {
    }
}
