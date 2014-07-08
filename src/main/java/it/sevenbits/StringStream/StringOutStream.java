package it.sevenbits.StringStream;

import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.IStream.OutStream;

/**
 * Class implements output stream as StringBuffer and write to current stream
 */
public class StringOutStream implements OutStream {
    StringBuffer stringBuffer;

    public StringOutStream() {
    }

    public StringOutStream(String outputString) {
        stringBuffer = new StringBuffer(outputString);
    }

    public String getString() {
        return stringBuffer.toString();
    }

    /**
     * @see it.sevenbits.IStream.OutStream#writeSymbol(char symbol)
     */
    @Override
    public void writeSymbol(char symbol) throws StreamException {
        stringBuffer.append(symbol);
    }

    /**
     * @see it.sevenbits.IStream.OutStream#close()
     */
    @Override
    public void close() throws StreamException {
    }
}
