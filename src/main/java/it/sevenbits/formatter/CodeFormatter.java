package it.sevenbits.formatter;

import it.sevenbits.IStream.InStream;
import it.sevenbits.IStream.OutStream;
import it.sevenbits.exceptions.FormatterException;
import it.sevenbits.exceptions.NotEnoughBracketsException;
import it.sevenbits.exceptions.StreamException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * * Class formatting input stream and returning result into output stream
 */
public class CodeFormatter {

    /**
     * Logger for formatter work
     */
    private Logger logger = Logger.getLogger(CodeFormatter.class);

    /**
     * Formats code from source stream and saving formatted code
     * in destination stream.
     *
     * @param source        - Input stream, inclusive unformatted code
     * @param formatOptions - format options for file
     * @param destination   - Output stream, inclusive formatted code
     * @throws it.sevenbits.exceptions.FormatterException
     */
    public final void format(final InStream source, final OutStream destination, final FormatOptions formatOptions) throws FormatterException {
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
        } catch (NotEnoughBracketsException e) {
            throw new FormatterException(e.getMessage());
        }
    }

    /**
     * Check nesting level
     *
     * @param nestingLevel - code nesting level
     * @throws it.sevenbits.exceptions.NotEnoughBracketsException - nesting level < 0 so code have more close brackets
     *                                                            then open brackets.
     */
    private void inputCloseBracketValidator(final int nestingLevel) throws NotEnoughBracketsException {
        if (nestingLevel < 0) {
            throw new NotEnoughBracketsException("Close brackets more then open brackets, invalid code on input stream. ");
        }
    }

    /**
     * Check nesting level
     *
     * @param nestingLevel - code nesting level
     * @throws it.sevenbits.exceptions.NotEnoughBracketsException - nesting level > 0 so code have more close brackets
     *                                                            then open brackets.
     */
    private void inputOpenBracketValidator(final int nestingLevel) throws NotEnoughBracketsException {
        if (nestingLevel > 0) {
            throw new NotEnoughBracketsException("Open brackets more then close brackets, invalid code on input stream. ");
        }
    }

    /**
     * Formats code after founding bracket
     *
     * @param destination   - Output stream, inclusive formatted code
     * @param formatOptions - format options for file
     * @param nestingLevel  - - code nesting level
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
}
