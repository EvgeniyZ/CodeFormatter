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

    /**
     * Logger for formatter work
     */
    private Logger logger = Logger.getLogger(Main.class.getName());

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
        Main main = new Main();
        main.logger.info("Start!");
        FileInStream fis;
        FileOutStream fos;
        FormatOptions formatOptions;
        try {
            formatOptions = new FormatOptions(DEFAULT_FORMATTER_PROPERTIES);
        } catch (StreamException ex) {
            if (main.logger.isEnabledFor(Level.DEBUG)) {
                main.logger.debug("\nFormatter properties not found, working with default parameters");
            }
            formatOptions = new FormatOptions();
        }
        if ((args.length > 2) || (args.length == 0)) {
            main.logger.error("\nParameters: input_file_path output_file_path\n");
        }
        CodeFormatter codeFormatter = new CodeFormatter();
        if (args.length == 1) {
            try {
                fis = new FileInStream(args[0]);
                fos = new FileOutStream(DEFAULT_OUTPUT_PATH);
                codeFormatter.format(fis, fos, formatOptions);
            } catch (StreamException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            } catch (FormatterException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            }
        }
        if (args.length == 2) {
            try {
                fis = new FileInStream(args[0]);
                fos = new FileOutStream(args[1]);
                codeFormatter.format(fis, fos, formatOptions);
            } catch (StreamException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            } catch (FormatterException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            }
        }
    }
}

