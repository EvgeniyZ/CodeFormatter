package it.sevenbits;

import it.sevenbits.Exceptions.FormatterException;
import it.sevenbits.Exceptions.StreamException;
import it.sevenbits.FileStream.FileInStream;
import it.sevenbits.FileStream.FileOutStream;
import it.sevenbits.codeFormatter.CodeFormatter;
import it.sevenbits.codeFormatter.FormatOptions;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Formates compiled java code
 */

public final class Main {

    private Logger logger = Logger.getLogger(Main.class.getName());

    private Main() {
    }

    /**
     * Formates java code in given file and writes it into another
     *
     * @param args - args[0] - name of file with unformatted java code; args[1] - name of file to which will be written formatted java code
     */
    public static void main(final String[] args) throws FormatterException {
        Main main = new Main();
        main.logger.info("Start!");
        if (args.length > 1) {
            FileInStream fis;
            FileOutStream fos;
            CodeFormatter codeFormatter = new CodeFormatter();
            FormatOptions formatOptions = new FormatOptions();
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
        } else {
            main.logger.error("Parameters: input_file_path output_file_path");
        }
    }
}
