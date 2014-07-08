package it.sevenbits.codeFormatter;

import it.sevenbits.Exceptions.FormatterException;
import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.IStream.InStream;
import it.sevenbits.IStream.OutStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Class formatting input stream and returning result into output stream
 */
public class CodeFormatter {
    private Logger logger = Logger.getLogger(CodeFormatter.class);

    public CodeFormatter() {
    }

    /**
     * Check nesting level
     *
     * @throws it.sevenbits.Exceptions.FormatterException - nesting level < 0 so code have more close brackets
     *                                                    then open brackets.
     */
    private void inputCloseBracketValidator(final int nestingLevel) throws FormatterException {
        if (nestingLevel < 0) {
            throw new FormatterException("Close brackets more then open brackets, invalid code on input stream. ");
        }
    }

    /**
     * Check nesting level
     *
     * @throws it.sevenbits.Exceptions.FormatterException - nesting level > 0 so code have more close brackets
     *                                                    then open brackets.
     */
    private void inputOpenBracketValidator(final int nestingLevel) throws FormatterException {
        if (nestingLevel > 0) {
            throw new FormatterException("Open brackets more then close brackets, invalid code on input stream. ");
        }
    }

    /**
     * Formats code after founding bracket
     *
     * @param destination - Output stream, inclusive formatted code
     */
    private void shiftNextString(final OutStream destination, final FormatOptions formatOptions, final int nestingLevel) throws FormatterException {
        try {
            destination.writeSymbol(formatOptions.getSymbolEndOfString());
            for (int i = 0; i < formatOptions.getIndent() * nestingLevel; i++) {
                destination.writeSymbol(formatOptions.getTabSymbol());
            }
        } catch (StreamException e) {
            throw new FormatterException(e.getMessage());
        }
    }

    /**
     * Formats code from source stream and saving formatted code
     * in destination stream.
     *
     * @param source      - Input stream, inclusive unformatted code
     * @param destination - Output stream, inclusive formatted code
     * @throws it.sevenbits.Exceptions.FormatterException
     */
    public void format(final InStream source, final OutStream destination, final FormatOptions formatOptions) throws FormatterException {
        boolean isNewString = false;
        boolean isAloneSpaceButton = false;
        int nestingLevel = 0;
        char symbol;
        try {
            while (!source.isEnd()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.debug("Read symbol from stream. ");
                }
                symbol = source.readSymbol();
                switch (symbol) {
                    case '{':
                        destination.writeSymbol(symbol);
                        isNewString = true;
                        nestingLevel++;
                        shiftNextString(destination, formatOptions, nestingLevel);
                        break;
                    case '}':
                        isNewString = true;
                        nestingLevel--;
                        inputCloseBracketValidator(nestingLevel);
                        shiftNextString(destination, formatOptions, nestingLevel);
                        destination.writeSymbol(symbol);
                        break;
                    case ';':
                        destination.writeSymbol(symbol);
                        isNewString = true;
                        shiftNextString(destination, formatOptions, nestingLevel);
                        break;
                    case ' ':
                        if (!isNewString) {
                            if (isAloneSpaceButton) {
                                isAloneSpaceButton = false;
                                destination.writeSymbol(symbol);
                            }
                            break;
                        }
                    case '\n':
                        break;
                    default:
                        isNewString = false;
                        isAloneSpaceButton = true;
                        destination.writeSymbol(symbol);
                        break;
                }
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.debug("Symbol parsed. ");
                }
            }
            source.close();
            destination.close();
            inputOpenBracketValidator(nestingLevel);
        } catch (StreamException e) {
            throw new FormatterException(e.getMessage());
        } catch (FormatterException e) {
            throw new FormatterException(e.getMessage());
        }
    }
}
