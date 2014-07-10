package it.sevenbits.stringstream;

import it.sevenbits.exceptions.StreamException;
import it.sevenbits.IStream.OutStream;

/**
 * Class implements output stream as StringBuffer and write to current stream
 */
public class StringOutStream implements OutStream {

    /**
     * Output stream based on string buffer
     * */
    private StringBuffer stringBuffer;

    /**
     * Creates stream from string
     * */
    public StringOutStream(final String outputString) {
        stringBuffer = new StringBuffer(outputString);
    }

    /**
     * Get string from output string buffer
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
