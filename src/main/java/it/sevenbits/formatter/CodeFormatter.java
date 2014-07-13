package it.sevenbits.formatter;

import it.sevenbits.exceptions.FormatterException;
import it.sevenbits.exceptions.NotEnoughBracketsException;
import it.sevenbits.exceptions.StreamException;
import it.sevenbits.streams.InStream;
import it.sevenbits.streams.OutStream;
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
    public final void format(final InStream source,
                             final OutStream destination,
                             final FormatOptions formatOptions) throws FormatterException {
        boolean isNewString = false;
        boolean isSpaceBetweenWords = false;
        boolean isAloneSpaceButton = false;
        int nestingLevel = 0;
        char currentSymbol;
        char previousSymbol = '\0';
        try {
            while (!source.isEnd()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.debug("Read symbol from stream. ");
                }
                currentSymbol = source.readSymbol();
                switch (currentSymbol) {
                    case '{':
                        destination.writeSymbol(currentSymbol);
                        isNewString = true;
                        nestingLevel++;
                        shiftNextString(destination, formatOptions, nestingLevel);
                        break;
                    case '}':
                        isNewString = true;
                        nestingLevel--;
                        inputCloseBracketValidator(nestingLevel);
                        shiftNextString(destination, formatOptions, nestingLevel);
                        destination.writeSymbol(currentSymbol);
                        break;
                    case ';':
                        destination.writeSymbol(currentSymbol);
                        isNewString = true;
                        shiftNextString(destination, formatOptions, nestingLevel);
                        break;
                    case ' ':
                        if (!isNewString) {
                            if ((previousSymbol != ' ') && (previousSymbol != '}') && (previousSymbol != ')')) {
                                isSpaceBetweenWords = true;
                                break;
                            }
                            if ((isSpaceBetweenWords) && (currentSymbol == '(')) {
                                destination.writeSymbol(' ');
                                break;
                            }
                            if (isSpaceBetweenWords) {
                                break;
                            }
                            if (isAloneSpaceButton) {
                                isAloneSpaceButton = false;
                                destination.writeSymbol(currentSymbol);
                            }
                            break;
                        }
                    case '\n':
                        break;
                    default:
                        isNewString = false;
                        isAloneSpaceButton = true;
                        isSpaceBetweenWords = false;
                        destination.writeSymbol(currentSymbol);
                        break;
                }
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.debug("Symbol parsed. ");
                }
                previousSymbol = currentSymbol;
            }
            source.close();
            destination.close();
            inputOpenBracketValidator(nestingLevel);
        } catch (StreamException e) {
            if (logger.isEnabledFor(Level.FATAL)) {
                logger.fatal(e.getMessage());
            }
            throw new FormatterException(e);
        } catch (NotEnoughBracketsException e) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error(e.getMessage());
            }
            throw new FormatterException(e);
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
            throw new NotEnoughBracketsException("\nClose brackets more then open brackets, invalid code on input stream. \n");
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
            throw new NotEnoughBracketsException("\nOpen brackets more then close brackets, invalid code on input stream. \n");
        }
    }

    /**
     * Formats code after founding bracket
     *
     * @param destination   - Output stream, inclusive formatted code
     * @param formatOptions - format options for file
     * @param nestingLevel  - - code nesting level
     */
    private void shiftNextString(final OutStream destination,
                                 final FormatOptions formatOptions,
                                 final int nestingLevel) throws FormatterException {
        try {
            destination.writeSymbol(formatOptions.getSymbolEndOfString());
            for (int i = 0; i < formatOptions.getIndent() * nestingLevel; i++) {
                destination.writeSymbol(formatOptions.getTabSymbol());
            }
        } catch (StreamException e) {
            throw new FormatterException(e);
        }
    }
}
