package it.sevenbits.formatter;

import it.sevenbits.exceptions.FormatterException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Class have information about format options
 */
public class FormatOptions {

    /**
     * How much use tabulation symbol between constructions in output file
     */
    private int indentSize;

    /**
     * End of string symbol in output file. \n for Win, \r\n for Linux (not supported) and etc.
     */
    private char symbolEndOfString;

    /**
     * Tabulation symbol in output file. \t or space supported.
     */
    private char tabSymbol;

    /**
     * Standard indent size for tabulation if configuration don't entered
     */
    public static final int INDENT_SIZE = 4;

    /**
     * Default parameters for formatter
     */
    public FormatOptions() {
        setDefaultParam();
    }

    /**
     * Parameters for formatter, entered by user
     *
     * @param configName Name of file includes config parameters for formatter
     */
    public FormatOptions(final String configName) throws FormatterException {
        Logger logger = Logger.getLogger(FormatOptions.class.getName());
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configName));
            tabSymbol = properties.getProperty("indentChar").charAt(1);
            indentSize = Integer.parseInt(properties.getProperty("indentLength"));
            symbolEndOfString = '\n';
        } catch (FileNotFoundException ex) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error("Cannot find formatter.properties, run with default configuration. ");
            }
            setDefaultParam();
            throw new FormatterException("Cannot find your formatter.properties, run with default configuration.");
        } catch (IOException ex) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error(ex.getMessage());
            }
        } catch (NullPointerException ex) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error("Cannot find parameters in formatter.properties. Run with formatter.properties");
            }
            setDefaultParam();
            throw new FormatterException("Cannot find your parameters in formatter.properties, " +
                    "run with default configuration.");
        }
    }

    /**
     * Get indent size
     *
     * @return int
     */
    public final int getIndent() {
        return indentSize;
    }

    /**
     * Get tab symbol
     *
     * @return char
     */
    public final char getTabSymbol() {
        return tabSymbol;
    }

    /**
     * Get symbol end of string
     *
     * @return char
     */
    public final char getSymbolEndOfString() {
        return symbolEndOfString;
    }

    /**
     * Default parameters for formatter
     */
    private void setDefaultParam() {
        indentSize = INDENT_SIZE;
        symbolEndOfString = '\n';
        tabSymbol = ' ';
    }
}
