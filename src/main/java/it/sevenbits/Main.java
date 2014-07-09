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
     * */
    private static final String DEFAULT_FORMATTER_PROPERTIES = "formatter.properties";

    /**
     * Formates java code in given file and writes it into another
     *
     * @param args - args[0] - name of file with unformatted java code; args[1] - name of file to which will be written formatted java code
     * @throws it.sevenbits.exceptions.FormatterException
     */
    public static void main(final String[] args) throws FormatterException {
        Main main = new Main();
        main.logger.info("Start!");
        if (args.length == 2) {
            FileInStream fis;
            FileOutStream fos;
            CodeFormatter codeFormatter = new CodeFormatter();
            FormatOptions formatOptions = new FormatOptions(DEFAULT_FORMATTER_PROPERTIES);
            try {
                fis = new FileInStream(args[0]);
                fos = new FileOutStream(args[1]);
                codeFormatter.format(fis, fos, formatOptions);
            } catch (FormatterException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            } catch (StreamException ex) {
                if (main.logger.isEnabledFor(Level.ERROR)) {
                    main.logger.error(ex.getMessage());
                }
            }
        }
    }
}
