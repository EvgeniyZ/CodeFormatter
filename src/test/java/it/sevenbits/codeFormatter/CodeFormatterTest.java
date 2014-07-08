package it.sevenbits.codeFormatter;

import it.sevenbits.Exceptions.FormatterException;
import it.sevenbits.StringStream.StringInStream;
import it.sevenbits.StringStream.StringOutStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for format method CodeFormatter.
 */

public class CodeFormatterTest {
    private Logger logger = Logger.getLogger(CodeFormatterTest.class);
    private static final int STANDART_INDENT_SIZE = 4;

    @Test(expected = FormatterException.class)
    public void testFormatNullInput() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream();
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);

        formatter.format(inStream, outStream, formatOptions);
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("testFormatNullInput : " +
                    "We try to read from null string and catch exception - cool. ");
        }
    }

    @Test(expected = FormatterException.class)
    public void testFormatTooManyBrackets() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{{{{}");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);

        formatter.format(inStream, outStream, formatOptions);
        if (logger.isEnabledFor(Level.FATAL)) {
            logger.fatal("testFormatNullInput : " +
                    "We can't read from null string, but we did it. ");
        }
    }

    @Test
    public void testFormatEmptyString() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);
        String expectedString = "";

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(outStream.getString(), expectedString);
        if (logger.isEnabledFor(Level.DEBUG))
            logger.debug("testFormatEmptyString :" +
                    " Correct output with empty string. ");
    }

    @Test
    public void testFormatCorrectInput() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{                {                         }            }");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);
        String expectedString = new String("{\n" + "    " + "{\n" + "        " + "\n    }\n" + "}");

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("testFormatCorrectInput :" +
                    " Correct output with this current string. ");
        }
    }

    @Test
    public void testFormatPointComma() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("a;");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);
        String expectedString = new String("a;\n");

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("testFormatPointComma : " +
                    "Correct output with current input string. ");
        }
    }

    @Test
    public void testFormatSomeCode() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{do()                     {while()}          }");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions(STANDART_INDENT_SIZE, true);
        String expectedString = new String("{\n" +
                "    do() {\n" +
                "        while()\n" +
                "    }\n" +
                "}");

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("testFormatSomeCode : " +
                    "Correct output with current input string. ");
        }
    }
}