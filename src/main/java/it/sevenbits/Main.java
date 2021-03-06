package it.sevenbits;

import it.sevenbits.exceptions.FormatterException;
import it.sevenbits.exceptions.StreamException;
import it.sevenbits.filestream.FileInStream;
import it.sevenbits.filestream.FileOutStream;
import it.sevenbits.formatter.CodeFormatter;
import it.sevenbits.formatter.FormatOptions;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Formates compiled java code
 */

public final class Main {

    private Main() {}

    /**
     * Logger for formatter work
     */
    private static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * User properties for formatter
     */
    private static final String DEFAULT_FORMATTER_PROPERTIES = "formatter.properties";

    /**
     * Default path to output file if user don't entered output file path
     */
    private static final String DEFAULT_OUTPUT_PATH = "output";

    /**
     * Format java code in given file and writes it into another
     *
     * @param args - args[0] - name of file with unformatted java code; args[1] - name of file to which will be written formatted java code
     * @throws it.sevenbits.exceptions.FormatterException
     */
    public static void main(final String[] args) throws FormatterException {
        logger.info("Start!");
        FileInStream fis;
        FileOutStream fos;
        FormatOptions formatOptions;
        try {
            formatOptions = new FormatOptions(DEFAULT_FORMATTER_PROPERTIES);
        } catch (FormatterException ex) {
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.debug("\nFormatter properties not found, run default parameters");
            }
            System.out.println("Formatter properties not found, working with default parameters.");
            formatOptions = new FormatOptions();
        }
        if ((args.length > 2) || (args.length == 0)) {
            logger.error("\nParameters only: input_file_path output_file_path\n");
        }
        CodeFormatter codeFormatter = new CodeFormatter();
        if (args.length == 1) {
            try {
                fis = new FileInStream(args[0]);
                fos = new FileOutStream(DEFAULT_OUTPUT_PATH);
                codeFormatter.format(fis, fos, formatOptions);
                System.out.println("Your code has been formatted in /yourproject/input file");
            } catch (StreamException ex) {
                if (logger.isEnabledFor(Level.ERROR)) {
                    logger.error(ex.getMessage());
                }
                System.out.println("Can't format your file. Check the input properties.");
            } catch (FormatterException ex) {
                if (logger.isEnabledFor(Level.ERROR)) {
                    logger.error(ex.getMessage());
                }
                System.out.println("Can't format your file. Are you sure that your code compiling?");
            }
        }
        if (args.length == 2) {
            try {
                fis = new FileInStream(args[0]);
                fos = new FileOutStream(args[1]);
                codeFormatter.format(fis, fos, formatOptions);
                System.out.println("Your code has been formatted in your file output directory");
            } catch (StreamException ex) {
                if (logger.isEnabledFor(Level.ERROR)) {
                    logger.error(ex.getMessage());
                }
                System.out.println("Can't format your file. Check the input properties.");
            } catch (FormatterException ex) {
                if (logger.isEnabledFor(Level.ERROR)) {
                    logger.error(ex.getMessage());
                }
                System.out.println("Can't format your file. Are you sure that your code compiling?");
            }
        }
    }
}

