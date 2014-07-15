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
     * Default tabulation symbol in output file. \t or space supported.
     */
    public static final char INDENT_SYMBOL = ' ';

    /**
     * End of string symbol in output file \n.
     */
    public static final char SYMBOL_END_OF_STRING = '\n';

    /**
     * Standard indent size for tabulation if configuration don't entered.
     */
    public static final int INDENT_SIZE = 4;


    /**
     * Formats code from source stream and saving formatted code
     * in destination stream.
     *
     * @param source        - Input stream, inclusive unformatted code
     * @param formatOptions - format options for file
     * @param destination   - Output stream, inclusive formatted code
     * @throws it.sevenbits.exceptions.FormatterException
     */

    //TODO FIX PROBLEM WITH SPACES BETWEEN IF () ELSE {
    public final void format(
        final InStream source, final OutStream destination, final FormatOptions formatOptions
    ) throws FormatterException {
        char symbolEndOfString;
        char indentSymbol;
        int indentSize;
        if (formatOptions == null) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Null format options find, using default parameters");
            }
            symbolEndOfString = SYMBOL_END_OF_STRING;
            indentSymbol = INDENT_SYMBOL;
            indentSize = INDENT_SIZE;
        } else {
            symbolEndOfString = formatOptions.getSymbolEndOfString();
            indentSymbol = formatOptions.getTabSymbol();
            indentSize = formatOptions.getIndent();
        }
        boolean isShifting = false;
        boolean isNewString = false;
        boolean isSpaceBetweenWords = false;
        boolean isAloneSpaceButton = false;
        boolean closedBracketFinded = false;
        boolean isShiftingSymbol = false;
        int nestingLevel = 0;
        char currentSymbol;
        char previousSymbol = '\0';
        try {
            while (!source.isEnd()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.debug("Read symbol from stream. ");
                }
                currentSymbol = source.readSymbol();
                if ((closedBracketFinded) || (currentSymbol == ' ') || (currentSymbol == '\t')
                    || (currentSymbol == symbolEndOfString)) {
                    isShiftingSymbol = true;
                }
                if ((closedBracketFinded) && notShiftingSymbol(currentSymbol, symbolEndOfString)) {
                    destination.writeSymbol('}');
                    destination.writeSymbol(indentSymbol);
                    closedBracketFinded = false;
                    isShiftingSymbol = false;
                } else if ((closedBracketFinded) && (!isShiftingSymbol)) {
                    destination.writeSymbol(previousSymbol);
                    closedBracketFinded = false;
                }
                if ((isShifting) && (currentSymbol == '}')) {
                    isShifting = false;
                } else if ((isShifting) && notShiftingSymbol(currentSymbol, symbolEndOfString)) {
                    destination.writeSymbol(formatOptions.getSymbolEndOfString());
                    shiftNextString(destination, indentSymbol, indentSize, nestingLevel);
                    isShifting = false;
                }
                switch (currentSymbol) {
                    case '{':
                        if ((previousSymbol != ' ') && (previousSymbol != '\0') && (previousSymbol != '{')) {
                            destination.writeSymbol(indentSymbol);
                            destination.writeSymbol(currentSymbol);
                        } else {
                            destination.writeSymbol(currentSymbol);
                        }
                        isNewString = true;
                        nestingLevel++;
                        destination.writeSymbol(symbolEndOfString);
                        shiftNextString(destination, indentSymbol, indentSize, nestingLevel);
                        break;
                    case '}':
                        isNewString = true;
                        nestingLevel--;
                        inputCloseBracketValidator(nestingLevel);
                        destination.writeSymbol(symbolEndOfString);
                        shiftNextString(destination, indentSymbol, indentSize, nestingLevel);
                        closedBracketFinded = true;
                        break;
                    case ';':
                        destination.writeSymbol(currentSymbol);
                        isShifting = true;
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
            if (isShifting) {
                destination.writeSymbol(symbolEndOfString);
            }
            if (closedBracketFinded) {
                destination.writeSymbol('}');
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
     * @param destination  - Output stream, inclusive formatted code
     * @param indentSymbol - format options for file
     * @param nestingLevel - - code nesting level
     */
    private void shiftNextString(
        final OutStream destination, final char indentSymbol, final int indentSize, final int nestingLevel
    ) throws FormatterException {
        try {
            for (int i = 0; i < indentSize * nestingLevel; i++) {
                destination.writeSymbol(indentSymbol);
            }
        } catch (StreamException e) {
            throw new FormatterException(e);
        }
    }

    private boolean notShiftingSymbol(char currentSymbol, char symbolEndOfString) {
        return ((currentSymbol != ' ') && (currentSymbol != '\t') && (currentSymbol != symbolEndOfString));
    }
}
